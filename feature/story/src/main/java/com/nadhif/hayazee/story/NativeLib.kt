package com.nadhif.hayazee.story

class NativeLib {

    /**
     * A native method that is implemented by the 'story' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {
        // Used to load the 'story' library on application startup.
        init {
            System.loadLibrary("story")
        }
    }
}