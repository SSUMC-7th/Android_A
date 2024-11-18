package umc.study.umc_7th

import AlbumFragment
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.viewpager2.widget.ViewPager2
import umc.study.umc_7th.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), CommunicationInterface {

    lateinit var binding : FragmentHomeBinding

    private var albumDatas = ArrayList<Album>()
    private lateinit var songDB: SongDatabase

    private val timer = SongActivity.Timer()
    private val handler = Handler(Looper.getMainLooper())

    override fun sendData(album: Album) { // MainActivity의 UI를 업데이트하기 위해 사용하는 메서드
        if (activity is MainActivity) {
            val activity = activity as MainActivity
            activity.updateMainPlayerCl(album)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

//        binding.homeAlbumImgIv1.setOnClickListener {
//            val bundle = Bundle()
//            bundle.putString("title", binding.titleLilac.text.toString())
//            bundle.putString("singer", binding.singerIu.text.toString())
//
//            val albumFragment = AlbumFragment()
//            albumFragment.arguments = bundle
//
//            (context as MainActivity)
//                .supportFragmentManager.beginTransaction()
//                .replace(R.id.main_frm, albumFragment).commitAllowingStateLoss()
//        }

        inputDummyAlbums()

        songDB = SongDatabase.getInstance(requireContext())!!
        albumDatas.addAll(songDB.albumDao().getAlbums())
        Log.d("albumlist", albumDatas.toString())

        val albumRVAdapter = AlbumRVAdapter(albumDatas)
        binding.homeTodayMusicAlbumRv.adapter = albumRVAdapter
        binding.homeTodayMusicAlbumRv.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)

        albumRVAdapter.setItemClickListener(object : AlbumRVAdapter.OnItemClickListener {
            override fun onItemClick(album : Album) {
                changeAlbumFragment(album)
            }

            override fun onPlayAlbum(album: Album) {
                sendData(album)
            }
        })

        val bannerAdapter = BannerVPAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))

        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.homeBannerIndicator.setViewPager(binding.homeBannerVp)

        autoSlide(bannerAdapter)

        val pannelAdpater = PannelVpAdapter(this)
        pannelAdpater.addFragment(PannelFragment(R.drawable.img_first_album_default))
        pannelAdpater.addFragment(PannelFragment(R.drawable.img_first_album_default))
        binding.homePannelBackgroundVp.adapter = pannelAdpater
        binding.homePannelBackgroundVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.homePannelIndicator.setViewPager(binding.homePannelBackgroundVp)

        binding.homePannelBtnMemoIv.setOnClickListener {
            val intent = Intent(requireActivity(), MemoActivity::class.java)
            val activity = requireActivity() // fragment에서 SharedPreferences에 접근하려면 context가 필요함.
            val sharedPreferences = activity.getSharedPreferences("memo", AppCompatActivity.MODE_PRIVATE)
            val tempMemo = sharedPreferences.getString("tempMemo", null)

            if(tempMemo != null) {
                val dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog, null)
                val builder = AlertDialog.Builder(activity)
                    .setView(dialogView)
                    .setTitle("메모 복원하기")

                val alertDialog = builder.show()
                val yesBtn = alertDialog.findViewById<Button>(R.id.yes)
                val noBtn = alertDialog.findViewById<Button>(R.id.no)
                yesBtn!!.setOnClickListener {
                    startActivity(intent)
                }

                noBtn!!.setOnClickListener {
                    val editor = sharedPreferences.edit()
                    editor.remove("tempMemo")
                    editor.apply()
                    startActivity(intent)
                }

                alertDialog.dismiss()
            } else {
                startActivity(intent)
            }

        }

        return binding.root
    }

    private fun autoSlide(adapter: BannerVPAdapter) {
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                handler.post {
                    val nextItem = binding.homeBannerVp.currentItem + 1
                    if (nextItem < adapter.itemCount) {
                        binding.homeBannerVp.currentItem = nextItem
                    } else {
                        binding.homeBannerVp.currentItem = 0 // 순환
                    }
                }
            }
        }, 3000, 3000)
    }

    private fun changeAlbumFragment(album: Album) {
        (context as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, AlbumFragment().apply {
                arguments = Bundle().apply {
                    val gson = Gson()
                    val albumToJson = gson.toJson(album)
                    putString("album", albumToJson)
                }
            })
            .commitAllowingStateLoss()
    }

    private fun inputDummyAlbums(){
        val songDB = SongDatabase.getInstance(requireActivity())!!
        val songs = songDB.albumDao().getAlbums()

        if (songs.isNotEmpty()) return

        songDB.albumDao().insert(
            Album(
                1,
                "IU 5th Album 'LILAC'",
                "아이유 (IU)",
                R.drawable.img_album_exp2
            )
        )

        songDB.albumDao().insert(
            Album(
                2,
                "Butter",
                "방탄소년단 (BTS)",
                R.drawable.img_album_exp
            )
        )

        songDB.albumDao().insert(
            Album(
                3,
                "iScreaM Vol.10: Next Level Remixes",
                "에스파 (AESPA)",
                R.drawable.img_album_exp3
            )
        )

        songDB.albumDao().insert(
            Album(
                4,
                "Map of the Soul Persona",
                "뮤직 보이 (Music Boy)",
                R.drawable.img_album_exp4,
            )
        )


        songDB.albumDao().insert(
            Album(
                5,
                "Great!",
                "모모랜드 (MOMOLAND)",
                R.drawable.img_album_exp5
            )
        )

        val songDBData = songDB.albumDao().getAlbums()
        Log.d("DB data", songDBData.toString())
    }
}