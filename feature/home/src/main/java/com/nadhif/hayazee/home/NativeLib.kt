package com.nadhif.hayazee.home

class NativeLib {

    /**
     * A native method that is implemented by the 'home' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {
        // Used to load the 'home' library on application startup.
        init {
            System.loadLibrary("home")
        }
    }
}