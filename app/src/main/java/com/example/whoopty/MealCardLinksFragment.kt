package com.example.whoopty

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import com.example.whoopty.utils.ShaderFactory
import com.example.whoopty.utils.StringFormater
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubeThumbnailLoader
import com.google.android.youtube.player.YouTubeThumbnailView

class MealCardLinksFragment(private val youtubeLink: String, private val activityContext: Context) :
    Fragment(), YouTubeThumbnailView.OnInitializedListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.meal_card_links_item, container, false)

        val titleView = view.findViewById<TextView>(R.id.meal_card_links_title)
        titleView.paint.shader = ShaderFactory.createRedGradientShader(activityContext)

        val youtubeThumb = view.findViewById<YouTubeThumbnailView>(R.id.meal_card_links_youtube)
        youtubeThumb.initialize(DeveloperKey().DEVELOPER_KEY, this)
        youtubeThumb.setOnClickListener(ClickListener())

        return view
    }

    override fun onInitializationSuccess(p0: YouTubeThumbnailView?, p1: YouTubeThumbnailLoader?) {
        p1?.setVideo(StringFormater.getYoutubeVideoCode(youtubeLink))
    }

    override fun onInitializationFailure(
        p0: YouTubeThumbnailView?,
        p1: YouTubeInitializationResult?
    ) {
        val REQUEST_CODE = 0

        if (p1?.isUserRecoverableError == true) {
            p1.getErrorDialog(activity, REQUEST_CODE).show()
        } else {
            val errorMessage = "There was an error initializing the YoutubePlayer $p1"
            Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    inner class ClickListener : View.OnClickListener {
        override fun onClick(p0: View?) {
            val intent = Intent(activityContext, YoutubeActivity::class.java).apply {
                putExtra("youtubeLink", youtubeLink)
            }

            startActivity(
                activityContext,
                intent,
                null
            )
        }
    }
}
