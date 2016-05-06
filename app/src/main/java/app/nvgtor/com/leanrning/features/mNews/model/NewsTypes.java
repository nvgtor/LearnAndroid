package app.nvgtor.com.leanrning.features.mNews.model;

/**
 * Created by nvgtor on 2015/5/25.
 */
public class NewsTypes
{
    private String ctype;
    private String page;
    private String ntype;
    private String platform;
    private String version;

    public NewsTypes(String ctype, String page, String ntype, String platform, String version)
    {
        this.ctype = ctype;
        this.page = page;
        this.ntype = ntype;
        this.platform = platform;
        this.version = version;
    }

    public String getCtype()
    {
        return ctype;
    }

    public void setCtype(String ctype)
    {
        this.ctype = ctype;
    }

    public String getPage()
    {
        return page;
    }

    public void setPage(String page)
    {
        this.page = page;
    }

    public String getNtype()
    {
        return ntype;
    }

    public void setNtype(String ntype)
    {
        this.ntype = ntype;
    }

    public String getPlatform()
    {
        return platform;
    }

    public void setPlatform(String platform)
    {
        this.platform = platform;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    @Override
    public String toString()
    {
        return "NewsTypes{" +
                "ctype='" + ctype + '\'' +
                ", page='" + page + '\'' +
                ", ntype='" + ntype + '\'' +
                ", platform='" + platform + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
