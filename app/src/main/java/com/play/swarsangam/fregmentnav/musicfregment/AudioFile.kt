package com.play.swarsangam.fregmentnav.musicfregment
import android.net.Uri

data class AudioFile(val id: Long,val title: String?, val artist: String?, val album: String?, val albumId: Long, val duration: Long,val  size: Long, val path: String?, val albumArtUri: Uri) {

}
