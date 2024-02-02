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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageSevice {
    private final ImageManager imageManager;
    private final DiaryRepository diaryRepository;

    @Transactional(readOnly = true)
    public List<byte[]> findDiaryImages(List<Long> diaryIds) {



    }


    @Transactional(readOnly = true)
    public List<byte[]> findOneDiaryImages(Long diaryId) {
        Diary diary = diaryRepository.getReferenceById(diaryId);
        List<Image> images = diary.getImages();

        if(!CollectionUtils.isEmpty(images)) {

        }
    }

    /* 이미지 저장 */
    @Transactional
    public void createOneDiaryImages(Long diayrId, List<MultipartFile> files) {
        log.info("create diaryFiles: {}", files);
        Diary diary = diaryRepository.getReferenceById(diayrId);

        List<Image> images = files.stream()
                .map(file -> Image.createAttachment(
                        diary,
                        imageManager.getFileName(file),
                        file.getOriginalFilename(),
                        file.getContentType(),
                        imageManager.uploadAndGetPath(file),
                        imageManager.compressImage(file)
                ))
                .collect(Collectors.toList());

        diary.getImages().addAll(images);
    }
    
    /* 이미지 수정 */
    @Transactional
    public void updateDiaryImages(Long diaryId, List<Long> deleteIds, List<MultipartFile> files) {
        if(!CollectionUtils.isEmpty(files)) { // 추가할 이미지가 있는 경우
            this.createOneDiaryImages(diaryId, files);
        }

        if(!CollectionUtils.isEmpty(deleteIds)) { // 삭제할 이미지가 있는 경우
            Diary diary = diaryRepository.getReferenceById(diaryId);
            List<Image> originImages = diary.getImages();
            originImages.removeIf(image -> deleteIds.contains(image.getId()));
        }
    }

}
