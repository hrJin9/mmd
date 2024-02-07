package com.mmd.image;

import com.mmd.entity.Image;
import com.mmd.entity.Diary;
import com.mmd.exception.ContentsNotFoundException;
import com.mmd.image.dto.ImageFindResultDto;
import com.mmd.repository.DiaryRepository;
import com.mmd.util.ImageManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageManager imageManager;
    private final DiaryRepository diaryRepository;
    private final EntityManager entityManager;
    
    /* 대표 이미지 목록 조회 */
    @Transactional(readOnly = true)
    public List<ImageFindResultDto> findDiaryImages(List<Long> diaryIds) {
        List<Diary> diaries = diaryRepository.findAllById(diaryIds);

        return diaries.stream()
                .map(diary -> {
                    String imageUrl = imageManager.findImageUrl(diary.getImages().get(0).getFileName());
                    return ImageFindResultDto.of(diary.getId(), imageUrl);
                })
                .collect(Collectors.toList());
    }


    /* 다이어리의 이미지 조회 */
    @Transactional(readOnly = true)
    public List<String> findOneDiaryImages(Long diaryId) {
        Diary diary = diaryRepository.getReferenceById(diaryId);
        List<Image> images = diary.getImages();

        return images.stream()
                .map(Image::getFileName)
                .map(imageManager::findImageUrl)
                .collect(Collectors.toList());
    }

    /* 이미지 저장 */
    @Transactional
    public void createOneDiaryImages(Long diaryId, List<MultipartFile> images) {
        Diary diary = diaryRepository.getReferenceById(diaryId);

        // aws s3 업로드
        List<String> s3UploadedImages = images.stream()
                .map(imageManager::uploadImages)
                .collect(Collectors.toList());

        // local 업로드
        List<Image> localUploadedImages = s3UploadedImages.stream()
                .map(fileName -> Image.upload(
                        diary,
                        fileName
                ))
                .collect(Collectors.toList());

        diary.getImages().addAll(localUploadedImages);
    }
    
    /* 이미지 수정 */
    @Transactional
    public void updateDiaryImages(Long diaryId, List<String> deleteFileNames, List<MultipartFile> images) {
        // 이미지 저장
        if(!CollectionUtils.isEmpty(images)) {
            this.createOneDiaryImages(diaryId, images);
        }

        // 이미지 삭제
        if(!CollectionUtils.isEmpty(deleteFileNames)) {
            Diary diary = diaryRepository.getReferenceById(diaryId);
            List<Image> originImages = diary.getImages();

//            if(originImages.stream().noneMatch(image -> deleteFileNames.contains(image.getFileName()))) {
//                throw new ContentsNotFoundException("삭제할 이미지가 존재하지 않습니다.");
//            }
//
//            // aws s3 삭제
//            deleteFileNames.forEach(imageManager::deleteImages);

            // local 삭제
            // TODO: 더티체킹 왜 안되는지??
            originImages.removeIf(image -> deleteFileNames.contains(image.getFileName()));

        }
    }

}
