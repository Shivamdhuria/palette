package com.elixer.palette.constraints

sealed class HorizontalAxis() {

    object Start : HorizontalAxis()
    object Center : HorizontalAxis()
    object End : HorizontalAxis()
}

sealed class VerticalAxis() {

    object Top : VerticalAxis()
    object Middle : VerticalAxis()
    object Bottom : VerticalAxis()
}