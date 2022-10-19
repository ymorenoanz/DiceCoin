package com.yaritzama

sealed class MainViewContainerState
{
    object ViewOne : MainViewContainerState()
    object ViewTwo : MainViewContainerState()
}