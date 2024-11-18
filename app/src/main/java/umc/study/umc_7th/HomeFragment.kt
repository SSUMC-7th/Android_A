package umc.study.umc_7th

import AlbumFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.viewpager2.widget.ViewPager2
import umc.study.umc_7th.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var binding : FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.homePannelAlbumImg01Iv.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("tilte", binding.titleLilacTv.text.toString())
            bundle.putString("singer", binding.singerIuTv.text.toString())
            bundle.putString("img", binding.homeDailyMusicAlbumImg01Iv.toString())

            val albumFragment = AlbumFragment()
            albumFragment.arguments = bundle

            (context as MainActivity)
                .supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, AlbumFragment()).commitAllowingStateLoss()

            val pannelAdpater = PannelVpAdapter(this)
            pannelAdpater.addFragment(PannelFragment(R.drawable.img_first_album_default))
            pannelAdpater.addFragment(PannelFragment(R.drawable.img_first_album_default))
            binding.homePannelBackgroundVp.adapter = pannelAdpater
            binding.homePannelBackgroundVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

            return binding.root
        }


        binding.homeDailyMusicAlbumImg01Iv.setOnClickListener(){
            setFragmentResult("TitleInfo", bundleOf("title" to binding.titleLilacTv.text.toString()))
            setFragmentResult("SingerInfo", bundleOf("singer" to binding.singerIuTv.text.toString()))
        }

        return binding.root
    }



}