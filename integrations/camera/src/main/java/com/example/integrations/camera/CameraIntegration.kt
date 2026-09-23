package com.example.integrations.camera

interface CameraIntegration {
    fun startPreview()
    fun capturePhoto(onResult: (Result<ByteArray>) -> Unit)
    fun stop()
}
