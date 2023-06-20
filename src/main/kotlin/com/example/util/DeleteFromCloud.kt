package com.example.util

import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.storage.BlobId
import com.google.cloud.storage.StorageOptions
import java.io.FileInputStream

fun deleteFromGoogleCloudStorage(fileUrl: String) {
    val blobName = fileUrl.substringAfterLast('/')
    val creds = GoogleCredentials.fromStream(FileInputStream("src/main/resources/your-credentials.json"))
    val storage = StorageOptions.newBuilder().setCredentials(creds).build().service

    val bucketName = "tajaqar"

    val blobId = BlobId.of(bucketName, blobName)

    storage.delete(blobId)
}