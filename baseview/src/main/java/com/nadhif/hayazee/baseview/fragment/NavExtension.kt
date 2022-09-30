package com.nadhif.hayazee.baseview.fragment

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest


fun NavController.navigateDeepLink(uri: Uri) {
    val navDeepLinkBuilder = NavDeepLinkRequest.Builder
        .fromUri(uri)
        .build()
    this.navigate(navDeepLinkBuilder)
}