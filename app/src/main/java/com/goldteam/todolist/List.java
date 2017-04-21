package com.goldteam.todolist;


public class List {
    private String name;
    private int resourceId;
    private String degrees;


    public static final List[] LISTs = {
            new List("Bruce Elenbogen", R.drawable.bruce_elenbogen, "Ph.D, Applied Mathematics, Northwestern University; MS, Computer Science, University of Michigan; BA, Math and Physics, Carleton College"),
            new List("Bruce Maxim", R.drawable.bruce_maxim, "PhD, Mathematics Education, University of Michigan; MA, Mathematics Education, University of Michigan; BS Ed, Mathematics Education, University of Michigan"),
            new List("Shengquan Wang", R.drawable.shengquan_wang, "PhD, Computer Science, Texas A&M University; MS, Mathematics, Texas A&M University; MS, Applied Mathematics, Shanghai Jiao Tong University; BS, Mathematics, Anhui Normal University"),
            new List("Luis Ortiz", R.drawable.luis_ortiz, "PhD, Computer Science, Brown University; BS, Computer Science, Institute of Technology at the University of Minnesota, Twin Cities"),
            new List("William Growsky", R.drawable.william_growsky, "PhD, Engineering and Applied Science, Yale University, 1971; MS, Applied Mathematics, Brown University, 1968; BS, Mathematics, MIT, 1965")
    };

    private List(String name, int resourceId, String degrees) {
        this.name = name;
        this.resourceId = resourceId;
        this.degrees = degrees;
    }

    public String getName() {
        return name;
    }

    public int getResourceId() {
        return resourceId;
    }

    public String getDegrees() {
        return degrees;
    }

    public String toString() {
        return this.name;
    }
}
