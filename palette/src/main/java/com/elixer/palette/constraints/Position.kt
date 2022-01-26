package com.elixer.palette.constraints

sealed class HorizontalAlignment() {

    object Start : HorizontalAlignment()
    object Center : HorizontalAlignment()
    object End : HorizontalAlignment()
}

sealed class VerticalAlignment() {

    object Top : VerticalAlignment()
    object Middle : VerticalAlignment()
    object Bottom : VerticalAlignment()
}