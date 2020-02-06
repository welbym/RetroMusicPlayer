/*
 * Copyright (c) 2019 Hemanth Savarala.
 *
 * Licensed under the GNU General Public License v3
 *
 * This is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by
 *  the Free Software Foundation either version 3 of the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 */

package code.name.monkey.retromusic.mvp.presenter

import code.name.monkey.retromusic.Result.Success
import code.name.monkey.retromusic.model.Album
import code.name.monkey.retromusic.model.Artist
import code.name.monkey.retromusic.model.Song
import code.name.monkey.retromusic.mvp.BaseView
import code.name.monkey.retromusic.mvp.Presenter
import code.name.monkey.retromusic.mvp.PresenterImpl
import code.name.monkey.retromusic.providers.interfaces.Repository
import code.name.monkey.retromusic.rest.model.LastFmArtist
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Created by hemanths on 20/08/17.
 */
interface ArtistDetailsView : BaseView {

    fun songs(songs: List<Song>)

    fun albums(albums: List<Album>)

    fun artist(artist: Artist)

    fun artistInfo(lastFmArtist: LastFmArtist?)

    fun complete()
}

interface ArtistDetailsPresenter : Presenter<ArtistDetailsView> {

    fun loadArtist(artistId: Long)

    fun loadArtistSongs(artistId: Long)

    fun loadArtistAlbums(artistId: Long)

    fun loadBiography(
        name: String,
        lang: String? = Locale.getDefault().language,
        cache: String?
    )

    class ArtistDetailsPresenterImpl @Inject constructor(
        private val repository: Repository
    ) : PresenterImpl<ArtistDetailsView>(), ArtistDetailsPresenter, CoroutineScope {

        override val coroutineContext: CoroutineContext
            get() = Dispatchers.IO + job

        private val job = Job()

        override fun loadBiography(name: String, lang: String?, cache: String?) {
            launch {
                when (val result = repository.artistInfo(name, lang, cache)) {
                    is Success -> withContext(Dispatchers.Main) { view?.artistInfo(result.data) }
                    is Error -> withContext(Dispatchers.Main) {}
                }
            }
        }

        override fun loadArtist(artistId: Long) {
            launch {
                when (val result = repository.artistById(artistId)) {
                    is Success -> withContext(Dispatchers.Main) { view?.artist(result.data) }
                    is Error -> withContext(Dispatchers.Main) { view?.showEmptyView() }
                }
            }
        }

        override fun loadArtistSongs(artistId: Long) {
            launch {
                when (val result = repository.artistSongsById(artistId)) {
                    is Success -> withContext(Dispatchers.Main) { view.songs(result.data) }
                    is Error -> withContext(Dispatchers.Main) {}
                }
            }
        }

        override fun loadArtistAlbums(artistId: Long) {
            launch {
                when (val result = repository.artistAlbumsById(artistId)) {
                    is Success -> withContext(Dispatchers.Main) { view.albums(result.data) }
                    is Error -> withContext(Dispatchers.Main) {}
                }
            }
        }

        override fun detachView() {
            super.detachView()
            job.cancel()
        }
    }
}