package com.example.mock

import java.io.InputStream

object MockImageStorage {
    private const val IMAGE_DIRECTORY = "images"
    private val images = mutableMapOf<Long, String>()

    init {
        // 샘플 이미지 파일명 매핑
        images[101] = "img_album_exp1.png"
        images[102] = "img_album_exp2.png"
        images[103] = "img_album_exp3.jpg"
        images[104] = "img_album_exp4.jpg"
        images[105] = "img_album_exp5.jpg"
        images[106] = "img_album_exp6.jpg"
        images[107] = "img_album_exp7.jpg"
        images[108] = "img_album_exp8.jpg"
        images[109] = "img_album_exp9.jpg"
        images[201] = "img_podcast_exp.png"
        images[301] = "img_video_exp.png"
    }

    fun getImageInputStream(imageId: Long): InputStream? {
        val fileName = images[imageId] ?: return null
        return javaClass.classLoader?.getResourceAsStream("$IMAGE_DIRECTORY/$fileName")
    }

    fun addImage(imageId: Long, fileName: String) {
        images[imageId] = fileName
    }

    fun removeImage(imageId: Long) {
        images.remove(imageId)
    }

    fun getImageFileName(imageId: Long): String? {
        return images[imageId]
    }
}