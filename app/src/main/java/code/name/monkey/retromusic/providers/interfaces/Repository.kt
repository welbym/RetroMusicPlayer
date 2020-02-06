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

package code.name.monkey.retromusic.providers.interfaces

import code.name.monkey.retromusic.Result
import code.name.monkey.retromusic.model.Album
import code.name.monkey.retromusic.model.Artist
import code.name.monkey.retromusic.model.Genre
import code.name.monkey.retromusic.model.Home
import code.name.monkey.retromusic.model.Playlist
import code.name.monkey.retromusic.model.Song
import code.name.monkey.retromusic.rest.model.LastFmAlbum
import code.name.monkey.retromusic.rest.model.LastFmArtist

/**
 * Created by hemanths on 11/08/17.
 */

interface Repository {

    suspend fun allAlbums(): Result<List<Album>>

    suspend fun albumById(albumId: Long): Result<Album>

    suspend fun albumSongsById(albumId: Long): Result<List<Song>>

    suspend fun artistSongsById(artistId: Long): Result<List<Song>>

    suspend fun artistAlbumsById(artistId: Long): Result<List<Album>>

    suspend fun allSongs(): Result<List<Song>>

    suspend fun allArtists(): Result<List<Artist>>

    suspend fun allPlaylists(): Result<List<Playlist>>

    suspend fun allGenres(): Result<List<Genre>>

    suspend fun search(query: String?): Result<MutableList<Any>>

    suspend fun getPlaylistSongs(playlist: Playlist): Result<List<Song>>

    suspend fun getGenre(genreId: Int): Result<List<Song>>

    suspend fun recentArtists(): Result<Home>

    suspend fun topArtists(): Result<Home>

    suspend fun topAlbums(): Result<Home>

    suspend fun recentAlbums(): Result<Home>

    suspend fun favoritePlaylist(): Result<Home>

    suspend fun artistInfo(name: String, lang: String?, cache: String?): Result<LastFmArtist>

    suspend fun albumInfo(artist: String, album: String): Result<LastFmAlbum>

    suspend fun artistById(artistId: Long): Result<Artist>
}