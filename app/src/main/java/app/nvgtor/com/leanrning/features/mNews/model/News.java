package app.nvgtor.com.leanrning.features.mNews.model;

import java.io.Serializable;

/**
 * Created by nvgtor on 2015/5/26.
 */
public class News implements Serializable{
    private String index;
    private String subject;
    private String addat;

    public String getIndex()
    {
        return index;
    }

    public void setIndex(String index)
    {
        this.index = index;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public String getAddat()
    {
        return addat;
    }

    public void setAddat(String addat)
    {
        this.addat = addat;
    }

    @Override
    public String toString()
    {
        return "NewsList{" +
                "index='" + index + '\'' +
                ", subject='" + subject + '\'' +
                ", addat='" + addat + '\'' +
                '}';
    }
}
