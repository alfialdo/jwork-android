package alfialdo.jwork_android.object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class untuk membuat object Location yang akan menyimpan data sementara
 * @author Muhammad Alfi A
 * @version Final Project - 20 June 2021
 */
public class Location implements Parcelable
{
    private String province;
    private String city;
    private String description;

    public Location(String province, String description, String city)
    {
        this.province = province;
        this.city = city;
        this.description = description;
    }

    protected Location(Parcel in)
    {
        province = in.readString();
        city = in.readString();
        description = in.readString();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(province);
        dest.writeString(city);
        dest.writeString(description);
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    public static final Creator<Location> CREATOR = new Creator<Location>()
    {
        @Override
        public Location createFromParcel(Parcel in)
        {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size)
        {
            return new Location[size];
        }
    };

    public String getProvince()
    {
        return province;
    }

    public void setProvince(String province)
    {
        this.province = province;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

}
