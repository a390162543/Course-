package cn.zhsite.extra;

public class Inform {

    private String title;
    private String content;
    private String link;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLink() {
        if(link == null){
            return null;
        }
        if(content == null){
            return "点击<a href="+link+">这里</a>返回";
        }
        return "<br>点击<a href="+link+">这里</a>返回";
    }

    public void setLink(String link) {
        this.link = link;
    }
}
