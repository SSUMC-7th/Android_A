package com.example.mock

object MockDatabase {
    private val musics = mutableListOf<MusicContent>()
    private val albums = mutableListOf<Album>()
    private val podcasts = mutableListOf<PodcastContent>()
    private val videos = mutableListOf<VideoContent>()
    private val authors = mutableListOf<Author>()

    fun getMusicById(id: Long): MusicContent? = musics.find { it.id == id }
    fun getMusicByAlbum(albumId: Long): List<MusicContent> = musics.filter { it.albumId == albumId }
    fun getAlbumById(id: Long): Album? = albums.find { it.id == id }
    fun getPodcastById(id: Long): PodcastContent? = podcasts.find { it.id == id }
    fun getVideoById(id: Long): VideoContent? = videos.find { it.id == id }
    fun getRandomMusicList(size: Int): List<MusicContent> = List(size) { musics.random() }
    fun getRandomAlbumList(size: Int): List<Album> = List(size) { albums.random() }
    fun getRandomPodcastList(size: Int): List<PodcastContent> = List(size) { podcasts.random() }
    fun getRandomVideoList(size: Int): List<VideoContent> = List(size) { videos.random() }
    fun getAuthorById(id: Long): Author? = authors.find { it.id == id }

    init {
        // 콘텐츠 제작자 추가
        var authorId: Long = 0
        authors.addAll(
            listOf(
                Author(++authorId, "BTS", null),
                Author(++authorId, "IU", null),
                Author(++authorId, "aespa", null),
                Author(++authorId, "모모랜드", null),
                Author(++authorId, "태연", null),
                Author(++authorId, "IVE", null),
                Author(++authorId, "김시선", null),
                Author(++authorId, "Unknown", null),
            )
        )
        
        // 앨범 추가
        var contentId: Long = 0
        albums.addAll(
            listOf(
                Album(++contentId, "Butter (feat. Megan Thee Stallion)", 1L, 101, "2021-05-21", "싱글", "댄스 팝"),
                Album(++contentId, "IU 5th Album \"LILAC\"", 2L, 102, "2021-03-25", "정규", "댄스 팝"),
                Album(++contentId, "Next Level", 3L, 103, "2021-05-17", "싱글", "댄스 팝"),
                Album(++contentId, "MAP OF THE SOUL : PERSONA", 1L, 104, "2019-04-12", "미니", "댄스 팝"),
                Album(++contentId, "Fun to The World", 4L, 105, "2018-06-26", "미니", "하우스"),
                Album(++contentId, "Weekend", 5L, 106, "2021-07-06", "싱글", "시티 팝"),
                Album(++contentId, "IVE SWITCH THE 2ND EP ALBUM", 6L, 107, "2024-04-29", "EP", "댄스 팝"),
                Album(++contentId, "Love wins all", 2L, 108, "2024-01-24", "싱글", "발라드"),
                Album(++contentId, "Drama : The 4th Mini Album", 3L, 109, "2023-11-10", "미니", "힙합"),
            )
        )

        // 음악 컨텐츠 추가
        musics.addAll(
            listOf(
                MusicContent(++contentId, "Butter", 1, 1, 0, "SINGLE", null),

                MusicContent(++contentId, "라일락", 2, 2, 0, "TITLE", null),
                MusicContent(++contentId, "Flu", 2, 2, 1, null, null),
                MusicContent(++contentId, "Coin", 2, 2, 2, "TITLE", null),
                MusicContent(++contentId, "봄 안녕 봄", 2, 2, 3, null, null),
                MusicContent(++contentId, "Celebrity", 2, 2, 4, null, null),
                MusicContent(++contentId, "돌림노래 (feat. DEAN)", 2, 2, 5, null, null),
                MusicContent(++contentId, "빈 컵 (Empty Cup)", 2, 2, 6, null, null),
                MusicContent(++contentId, "아이와 나의 바다", 2, 2, 7, null, null),
                MusicContent(++contentId, "어푸 (Ah puh)", 2, 2, 8, null, null),
                MusicContent(++contentId, "에필로그", 2, 2, 9, null, null),

                MusicContent(++contentId, "Next Level", 3, 3, 0, "SINGLE", null),

                MusicContent(++contentId, "Intro : Persona", 1, 4, 0, null, null),
                MusicContent(++contentId, "작은 것들을 위한 시 (Boy With Luv) (Feat. Halsey)", 1, 4, 1, "TITLE", null),
                MusicContent(++contentId, "소우주 (Mikrokosmos)", 1, 4, 2, null, null),
                MusicContent(++contentId, "Make It Right", 1, 4, 3, null, null),
                MusicContent(++contentId, "HOME", 1, 4, 4, null, null),
                MusicContent(++contentId, "Jamais Vu", 1, 4, 5, null, null),
                MusicContent(++contentId, "Dionysus", 1, 4, 6, null, null),

                MusicContent(++contentId, "BAAM", 4, 5, 0, "TITLE", null),
                MusicContent(++contentId, "베리 베리", 4, 5, 1, null, null),
                MusicContent(++contentId, "빙고게임", 4, 5, 2, null, null),
                MusicContent(++contentId, "Only one you", 4, 5, 3, null, null),
                MusicContent(++contentId, "BAAM (inst.)", 4, 5, 4, null, null),

                MusicContent(++contentId, "Weekend", 5, 6, 0, "SINGLE", null),

                MusicContent(++contentId, "해야 (HEYA)", 6, 7, 0, "TITLE", null),
                MusicContent(++contentId, "Accendio", 6, 7, 1, "TITLE", null),
                MusicContent(++contentId, "Blue Heart", 6, 7, 2, null, null),
                MusicContent(++contentId, "Ice Queen", 6, 7, 3, null, null),
                MusicContent(++contentId, "WOW", 6, 7, 4, null, null),
                MusicContent(++contentId, "RESET", 6, 7, 5, null, null),

                MusicContent(++contentId, "Love wins all", 2, 8, 0, null, null),

                MusicContent(++contentId, "Drama", 3, 9, 0, "TITLE", null),
                MusicContent(++contentId, "Trick or Trick", 3, 9, 1, null, null),
                MusicContent(++contentId, "Don't Blink", 3, 9, 2, null, null),
                MusicContent(++contentId, "Hot Air Balloon", 3, 9, 3, null, null),
                MusicContent(++contentId, "YOLO", 3, 9, 4, null, null),
                MusicContent(++contentId, "You", 3, 9, 5, null, null),
            )
        )

        // 팟캐스트 추가
        podcasts.addAll(
            listOf(
                PodcastContent(++contentId, "김시선의 귀책사유", 7, 201, "첫 번째 팟캐스트 에피소드"),
            )
        )

        // 비디오 추가
        videos.addAll(
            listOf(
                VideoContent(++contentId, "비디오 컨텐츠", 8, 301),
            )
        )
    }
}