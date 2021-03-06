package layout.com.anew.easyItalian.read;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import java.util.ArrayList;
import java.util.List;

import layout.com.anew.easyItalian.R;

public class ReadActivity extends Activity {


    private List<Article> articleList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ReadActivity.this);


        //初始化数据
        initArticle();


        //将articleTist里的文章展示出来
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        ArticleAdapter adapter=new ArticleAdapter(ReadActivity.this,articleList);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

    }

    //初始化数据
    private void initArticle(){
    //    for(int i=0;i<2;i++){
            /*
            *
            *
            * String title="Amo la mia mamma e il mio papa  ";
            String level="4星";
            String text="Per la maggior parte del mondo cristiano, il culmine della" +
                    " festività natalizia è il cenone di Natale. è il momento in cui le " +
                    "famiglie si riuniscono per festeggiare la vigilia di Natale che" +
                    " normalmente si celebra dopo la Messa di mezzanotte. In Francia " +
                    "questo pasto viene chiamato \"le reveillon\". Il cenone natalizio " +
                    "varia secondo le tradizioni culinarie, il menu consiste in anatra, " +
                    "paste fatte con farina di grano saraceno con panna acida, tacchino con " +
                    "castagne, mentre per i parigini, ostriche e Foie Gras. Una torta che " +
                    "porta il nome di \"La Bouche de Noel\" con la forma di un tronco d'albero" +
                    " viene consumata durante la reveillon. Lo Champagne, il vino frizzante " +
                    "prodotto nelle regione denominata Champagne, viene bevuto per celebrare " +
                    "entrambe le festività di Natale e di Capodanno."+i;
            String id="id"+i;
            Article article=new Article(id,title,level,text,"image1");*/

            Article article = getArticle("9900000");
            //将article放入链表中
            articleList.add(article);
            Article article1 = getArticle("9900001");
        //将article放入链表中
            articleList.add(article1);
            Article article2 = getArticle("9900002");
        //将article放入链表中
            articleList.add(article2);


        //}
    }

    private Article getArticle(String uid){
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());


        AVQuery<AVObject> query = new AVQuery<>("Articles");
        Article thisArticle;
        try {
            String thisTitle = query.whereEqualTo("UID",uid).getFirst().fetch().get("title").toString();
            String thisLevel = query.whereEqualTo("UID",uid).getFirst().fetch().get("level").toString();
            String thisImageUrl = "http://avisy.ddns.net:3322/" + query.whereEqualTo("UID",uid).getFirst().fetch().get("imageUrl").toString();
            String thisText = query.whereEqualTo("UID",uid).getFirst().fetch().get("text").toString();
            thisArticle = new Article(uid,thisTitle,thisLevel,thisText,thisImageUrl);
            return thisArticle;
        }catch (AVException e){
            Log.d("error","AVException=",e);
        }

        thisArticle = new Article("9900000","Error","0","Error","Error");
        return thisArticle;

    }

}
