package layout.com.anew.easyItalian.recite

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_word_imformation.*
import layout.com.anew.easyItalian.MainActivity
import layout.com.anew.easyItalian.R

class WordDetailsActivity() : Activity() {
    val word = Word()
    // getWord accepts the StringArrayList from ReciteWord or another Activity
    // and turn it to Word-info
    fun getWord() {
        val ins = intent
        val listdata = ins.getStringArrayListExtra("data")
        word.word=listdata[0]
        word.transform=listdata[1]
        word.translation=listdata[2]
        word.example=listdata[3]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_imformation)

        getWord()
        wordForDetails.setText(word.word)
        // set list for word details
        val data: Array<String>
        if(word.transform!=" " && word.example!=" ") {
            data = arrayOf(word.translation, word.transform, word.example)
        }else if (word.transform!=" " && word.example==" "){
            data = arrayOf(word.translation, word.transform)
        }else if (word.transform==" "&& word.example!=" "){
            data = arrayOf(word.translation, word.example)
        }else{
            data = arrayOf(word.translation)
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, data)
        val listView = findViewById<ListView>(R.id.wordDetails)
        listView.adapter = adapter

        voice.setOnClickListener{
            //call tts
            callTTS()
        }


        // the buttons' listener has problem
        val button = findViewById<Button>(R.id.continue_study)
        button.setOnClickListener {
            val intent = Intent(this, ReciteWordAcitivity::class.java)
            startActivity(intent)
        }

        val button_back = findViewById<Button>(R.id.back)
        button_back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
    //tts start
    private fun buildSpeechUrl(words: String): String {

        //tts from VoiceRss
        //val kVoiceRssServer = "http://api.voicerss.org"
        //val kVoiceRSSAppKey = "ec58f36cbbe846edb12b9fcc6e217d9f"
        //var url = ""
        //url = kVoiceRssServer + "/?key=" + kVoiceRSSAppKey + "&t=text&hl=" + "it-it" + "&src=" + words

        //tts from MARY TTS
        //http://mary.dfki.de:59125/process?INPUT_TEXT=pomodoro&INPUT_TYPE=TEXT&OUTPUT_TYPE=AUDIO&AUDIO=WAVE_FILE&LOCALE=it
        var url="http://mary.dfki.de:59125/process?INPUT_TEXT="
        val endIt="&INPUT_TYPE=TEXT&OUTPUT_TYPE=AUDIO&AUDIO=WAVE_FILE&LOCALE=it"
        url = url+words+endIt
        url = url.replace(' ','+')
        return url
    }

    fun btnSpeakNowOnClick() {
        var mp : MediaPlayer? = null
        val txtSentence = findViewById<TextView>(R.id.word)

        val text = txtSentence.text.toString()

        try {
            val onPreparedListener = MediaPlayer.OnPreparedListener {
                mp?.setVolume(1f, 1f)
                mp?.start()
            }

            val onErrorListener = MediaPlayer.OnErrorListener { _, _, _ -> false }

            val onCompletionListener = MediaPlayer.OnCompletionListener { mpt ->
                mpt.release()
            }

            val url = buildSpeechUrl(text)

            val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0)

            try {
                if (mp != null)
                    mp.release()

                mp = MediaPlayer()
                mp.setDataSource(url)
                mp.setOnErrorListener(onErrorListener)
                mp.setOnCompletionListener(onCompletionListener)
                mp.setOnPreparedListener(onPreparedListener)
                mp.prepareAsync()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun callTTS(){
        val txtSentence = findViewById<TextView>(R.id.wordForDetails)
        val text = txtSentence.text.toString()
        val url = buildSpeechUrl(text)
        val mediaPlayer = MediaPlayer()
        mediaPlayer.setDataSource(url)
        mediaPlayer.setOnErrorListener{_, _, _ -> false}
        mediaPlayer.setOnCompletionListener { mediaPlayer.release() }
        mediaPlayer.setOnPreparedListener {  mediaPlayer.start()}
        mediaPlayer.prepareAsync()
    }
    //tts end

}
