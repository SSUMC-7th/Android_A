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
    fun getRandomPodcastList(size: Int): List<PodcastContent> = List(size) { podcasts.random() }
    fun getRandomVideoList(size: Int): List<VideoContent> = List(size) { videos.random() }
    fun getAuthorById(id: Long): Author? = authors.find { it.id == id }

    init {
        // 콘텐츠 제작자 추가
        var seq: Long = 0
        authors.addAll(
            listOf(
                Author(++seq, "BTS", null),
                Author(++seq, "IU", null),
                Author(++seq, "aespa", null),
                Author(++seq, "모모랜드", null),
                Author(++seq, "태연", null),
                Author(++seq, "IVE", null),
                Author(++seq, "김시선", null),
                Author(++seq, "Unknown", null),
            )
        )
        
        // 앨범 추가
        seq = 0
        albums.addAll(
            listOf(
                Album(++seq, "Butter (feat. Megan Thee Stallion)", 1L, 101, "2021-05-21", "싱글", "댄스 팝"),
                Album(++seq, "IU 5th Album \"LILAC\"", 2L, 102, "2021-03-25", "정규", "댄스 팝"),
                Album(++seq, "Next Level", 3L, 103, "2021-05-17", "싱글", "댄스 팝"),
                Album(++seq, "MAP OF THE SOUL : PERSONA", 1L, 104, "2019-04-12", "미니", "댄스 팝"),
                Album(++seq, "Fun to The World", 4L, 105, "2018-06-26", "미니", "하우스"),
                Album(++seq, "Weekend", 5L, 106, "2021-07-06", "싱글", "시티 팝"),
                Album(++seq, "IVE SWITCH THE 2ND EP ALBUM", 6L, 107, "2024-04-29", "EP", "댄스 팝"),
                Album(++seq, "Love wins all", 2L, 108, "2024-01-24", "싱글", "발라드"),
                Album(++seq, "Drama : The 4th Mini Album", 3L, 109, "2023-11-10", "미니", "힙합"),
            )
        )

        // 음악 컨텐츠 추가
        seq = 0
        musics.addAll(
            listOf(
                MusicContent(++seq, "Butter", 1, 101, 200, 1, 0, "SINGLE", null),

                MusicContent(++seq, "라일락", 2, 102, 200, 2, 0, "TITLE", null),
                MusicContent(++seq, "Flu", 2, 102, 200, 2, 1, null, null),
                MusicContent(++seq, "Coin", 2, 102, 200, 2, 2, "TITLE", null),
                MusicContent(++seq, "봄 안녕 봄", 2, 102, 200, 2, 3, null, null),
                MusicContent(++seq, "Celebrity", 2, 102, 200, 2, 4, null, null),
                MusicContent(++seq, "돌림노래 (feat. DEAN)", 2, 102, 200, 2, 5, null, null),
                MusicContent(++seq, "빈 컵 (Empty Cup)", 2, 102, 200, 2, 6, null, null),
                MusicContent(++seq, "아이와 나의 바다", 2, 102, 200, 2, 7, null, null),
                MusicContent(++seq, "어푸 (Ah puh)", 2, 102, 200, 2, 8, null, null),
                MusicContent(++seq, "에필로그", 2, 102, 200, 2, 9, null, null),

                MusicContent(++seq, "Next Level", 3, 103, 200, 3, 0, "SINGLE", null),

                MusicContent(++seq, "Intro : Persona", 1, 104, 200, 4, 0, null, null),
                MusicContent(++seq, "작은 것들을 위한 시 (Boy With Luv) (Feat. Halsey)", 1, 104, 200, 4, 1, "TITLE", null),
                MusicContent(++seq, "소우주 (Mikrokosmos)", 1, 104, 200, 4, 2, null, null),
                MusicContent(++seq, "Make It Right", 1, 104, 200, 4, 3, null, null),
                MusicContent(++seq, "HOME", 1, 104, 200, 4, 4, null, null),
                MusicContent(++seq, "Jamais Vu", 1, 104, 200, 4, 5, null, null),
                MusicContent(++seq, "Dionysus", 1, 104, 200, 4, 6, null, null),

                MusicContent(++seq, "BAAM", 4, 105, 200, 5, 0, "TITLE", null),
                MusicContent(++seq, "베리 베리", 4, 105, 200, 5, 1, null, null),
                MusicContent(++seq, "빙고게임", 4, 105, 200, 5, 2, null, null),
                MusicContent(++seq, "Only one you", 4, 105, 200, 5, 3, null, null),
                MusicContent(++seq, "BAAM (inst.)", 4, 105, 200, 5, 4, null, null),

                MusicContent(++seq, "Weekend", 5, 106, 200, 6, 0, "SINGLE", null),

                MusicContent(++seq, "해야 (HEYA)", 6, 107, 200, 7, 0, "TITLE", null),
                MusicContent(++seq, "Accendio", 6, 107, 200, 7, 1, "TITLE", null),
                MusicContent(++seq, "Blue Heart", 6, 107, 200, 7, 2, null, null),
                MusicContent(++seq, "Ice Queen", 6, 107, 200, 7, 3, null, null),
                MusicContent(++seq, "WOW", 6, 107, 200, 7, 4, null, null),
                MusicContent(++seq, "RESET", 6, 107, 200, 7, 5, null, null),

                MusicContent(++seq, "Love wins all", 2, 108, 200, 8, 0, null, null),

                MusicContent(++seq, "Drama", 3, 109, 200, 9, 0, "TITLE", null),
                MusicContent(++seq, "Trick or Trick", 3, 109, 200, 9, 1, null, null),
                MusicContent(++seq, "Don't Blink", 3, 109, 200, 9, 2, null, null),
                MusicContent(++seq, "Hot Air Balloon", 3, 109, 200, 9, 3, null, null),
                MusicContent(++seq, "YOLO", 3, 109, 200, 9, 4, null, null),
                MusicContent(++seq, "You", 3, 109, 200, 9, 5, null, null),
            )
        )

        // 팟캐스트 추가
        podcasts.addAll(
            listOf(
                PodcastContent(++seq, "김시선의 귀책사유", 7, 201, 1800, "첫 번째 팟캐스트 에피소드"),
            )
        )

        // 비디오 추가
        seq = 0
        videos.addAll(
            listOf(
                VideoContent(++seq, "비디오 컨텐츠", 8, 301, 600),
            )
        )
    }
}