package com.play.swarsangam.fregmentnav.musicfregment

data class AudioFolder(val folderName: String)

fun setSongPosition(increment:Boolean){
    if (increment) {
        PlayerActivity.currentPosition++
        if (PlayerActivity.currentPosition >= PlayerActivity.songList.size) {
            PlayerActivity.currentPosition = 0
        }
    } else {
        PlayerActivity.currentPosition--
        if (PlayerActivity.currentPosition < 0) {
            PlayerActivity.currentPosition = PlayerActivity.songList.size - 1
        }
    }
}


