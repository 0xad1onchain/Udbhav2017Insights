package in.msritudbhav.udbhav2017insights.Wrappers;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Adi on 31/03/17.
 */
@IgnoreExtraProperties
public class Category {
    public String id;
    public String name;

    Category()
    {

    }

    Category(String id, String name)
    {
        this.id = id;
        this.name = name;
    }

}

