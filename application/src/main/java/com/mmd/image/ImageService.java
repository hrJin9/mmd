package com.mmd.image;

import com.mmd.entity.Image;
import com.mmd.entity.Diary;
import com.mmd.repository.DiaryRepository;
import com.mmd.util.ImageManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageManager imageManager;
    private final DiaryRepository diaryRepository;
    
    /* 대표 이미지 목록 조회 */
    @Transactional(readOnly = true)
    public List<String> findDiaryImages(List<Long> diaryIds) {
        List<Diary> diaries = diaryRepository.findAllById(diaryIds);

        List<Image> images = diaries.stream()
                .map(Diary::getImages)
                .filter(Objects::nonNull);


    }


    /* 다이어리의 이미지 조회 */
    @Transactional(readOnly = true)
    public List<String> findOneDiaryImages(Long diaryId) {
        Diary diary = diaryRepository.getReferenceById(diaryId);
        List<Image> images = diary.getImages();

        if(!CollectionUtils.isEmpty(images)) {
            return images.stream()
                    .map(Image::getFileName)
                    .map(imageManager::findImageUrl)
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    /* 이미지 저장 */
    @Transactional
    public List<String> createOneDiaryImages(Long diaryId, List<MultipartFile> images) {
        Diary diary = diaryRepository.getReferenceById(diaryId);

        if(!CollectionUtils.isEmpty(images)) {
            // aws s3 업로드
            List<String> s3UploadedImages = images.stream()
                    .map(imageManager::uploadImages)
                    .collect(Collectors.toList());
            
            // local 업로드
            List<Image> localUploadedImages = s3UploadedImages.stream()
                    .map(image -> Image.upload(
                            diary,
                            image
                    )).collect(Collectors.toList());
            
            diary.getImages().addAll(localUploadedImages);


            return s3UploadedImages;
        }

        return Collections.emptyList();
    }
    
    /* 이미지 수정 */
    @Transactional
    public void updateDiaryImages(Long diaryId, List<String> deleteFileNames, List<MultipartFile> images) {
        // 이미지 저장
        this.createOneDiaryImages(diaryId, images);
        
        // 이미지 삭제
        if(!CollectionUtils.isEmpty(deleteFileNames)) {
            Diary diary = diaryRepository.getReferenceById(diaryId);
            List<Image> originImages = diary.getImages();
//            originImages.removeIf(image -> .contains(image.getId()));

        }
    }

}
