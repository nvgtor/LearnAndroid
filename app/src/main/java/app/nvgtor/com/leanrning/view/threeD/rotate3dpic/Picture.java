package app.nvgtor.com.leanrning.view.threeD.rotate3dpic;

/**
 * Created by nvgtor on 2016/4/18.
 */
public class Picture {
    /**
     * 图片名称
     */
    private String name;

    /**
     * 图片对象的资源
     */
    private int resource;

    public Picture(String name, int resource) {
        this.name = name;
        this.resource = resource;
    }

    public String getName() {
        return name;
    }

    public int getResource() {
        return resource;
    }
}
