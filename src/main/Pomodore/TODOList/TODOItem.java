package main.Pomodore.TODOList;

public class TODOItem
{
    private String text;
    // Status of the Task (is the Task done or not?)
    private boolean status;

    public TODOItem(String text)
    {
        this.text = text;
        this.status = false;
    }

    public String getText()
    {
        return this.text;
    }

    public boolean getStatus()
    {
        return this.status;
    }

    public void setStatus(boolean status)
    {
        this.status = status;
    }
}
