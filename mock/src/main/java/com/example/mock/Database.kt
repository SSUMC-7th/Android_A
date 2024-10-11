package com.example.mock

object MockDatabase {
    private val musics = mutableListOf<MusicContent>()
    private val albums = mutableListOf<Album>()
    private val podcasts = mutableListOf<PodcastContent>()
    private val videos = mutableListOf<VideoContent>()
    private val authors = mutableListOf<Author>()

    fun getMusicById(id: Long): MusicContent? {
        return musics.find { it.id == id }
    }

    fun getAlbumById(id: Long): Album? {
        return albums.find { it.id == id }
    }

    fun getPodcastById(id: Long): PodcastContent? {
        return podcasts.find { it.id == id }
    }

    fun getVideoById(id: Long): VideoContent? {
        return videos.find { it.id == id }
    }

    fun getRandomMusicList(size: Int): List<MusicContent> {
        val list = mutableListOf<MusicContent>()
        while (list.size < size) {
            val music = musics.random()
            if (!list.contains(music)) list.add(music)
        }
        return list.toList()
    }

    fun getRandomPodcastList(size: Int): List<PodcastContent> {
        val list = mutableListOf<PodcastContent>()
        while (list.size < size) {
            val podcast = podcasts.random()
            if (!list.contains(podcast)) list.add(podcast)
        }
        return list.toList()
    }

    fun getRandomVideoList(size: Int): List<VideoContent> {
        val list = mutableListOf<VideoContent>()
        while (list.size < size) {
            val video = videos.random()
            if (!list.contains(video)) list.add(video)
        }
        return list.toList()
    }

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
                MusicContent(++seq, "Butter", 1, 101, 200, 1),

                MusicContent(++seq, "라일락", 2, 102, 200, 2),
                MusicContent(++seq, "Flu", 2, 102, 200, 2),
                MusicContent(++seq, "Coin", 2, 102, 200, 2),
                MusicContent(++seq, "봄 안녕 봄", 2, 102, 200, 2),
                MusicContent(++seq, "Celebrity", 2, 102, 200, 2),
                MusicContent(++seq, "돌림노래 (feat. DEAN)", 2, 102, 200, 2),
                MusicContent(++seq, "빈 컵 (Empty Cup)", 2, 102, 200, 2),
                MusicContent(++seq, "아이와 나의 바다", 2, 102, 200, 2),
                MusicContent(++seq, "어푸 (Ah puh)", 2, 102, 200, 2),
                MusicContent(++seq, "에필로그", 2, 102, 200, 2),

                MusicContent(++seq, "Next Level", 3, 103, 200, 3),

                MusicContent(++seq, "Intro : Persona", 1, 104, 200, 4),
                MusicContent(++seq, "작은 것들을 위한 시 (Boy With Luv) (Feat. Halsey)", 1, 104, 200, 4),
                MusicContent(++seq, "소우주 (Mikrokosmos)", 1, 104, 200, 4),
                MusicContent(++seq, "Make It Right", 1, 104, 200, 4),
                MusicContent(++seq, "HOME", 1, 104, 200, 4),
                MusicContent(++seq, "Jamais Vu", 1, 104, 200, 4),
                MusicContent(++seq, "Dionysus", 1, 104, 200, 4),

                MusicContent(++seq, "BAAM", 4, 105, 200, 5),
                MusicContent(++seq, "베리 베리", 4, 105, 200, 5),
                MusicContent(++seq, "빙고게임", 4, 105, 200, 5),
                MusicContent(++seq, "Only one you", 4, 105, 200, 5),
                MusicContent(++seq, "BAAM (inst.)", 4, 105, 200, 5),

                MusicContent(++seq, "Weekend", 5, 106, 200, 6),

                MusicContent(++seq, "해야 (HEYA)", 6, 107, 200, 7),
                MusicContent(++seq, "Accendio", 6, 107, 200, 7),
                MusicContent(++seq, "Blue Heart", 6, 107, 200, 7),
                MusicContent(++seq, "Ice Queen", 6, 107, 200, 7),
                MusicContent(++seq, "WOW", 6, 107, 200, 7),
                MusicContent(++seq, "RESET", 6, 107, 200, 7),

                MusicContent(++seq, "Love wins all", 2, 108, 200, 8),

                MusicContent(++seq, "Drama", 3, 109, 200, 9),
                MusicContent(++seq, "Trick or Trick", 3, 109, 200, 9),
                MusicContent(++seq, "Don't Blink", 3, 109, 200, 9),
                MusicContent(++seq, "Hot Air Balloon", 3, 109, 200, 9),
                MusicContent(++seq, "YOLO", 3, 109, 200, 9),
                MusicContent(++seq, "You", 3, 109, 200, 9),
            )
        )

        // 팟캐스트 추가
        seq = 0
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