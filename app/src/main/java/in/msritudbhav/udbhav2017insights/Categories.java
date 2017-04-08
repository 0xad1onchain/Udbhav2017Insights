package in.msritudbhav.udbhav2017insights;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Adi on 31/03/17.
 */
@IgnoreExtraProperties
public class Categories {
    public String id;
    public String name;

    Categories()
    {

    }

    Categories(String id, String name)
    {
        this.id = id;
        this.name = name;
    }

}

