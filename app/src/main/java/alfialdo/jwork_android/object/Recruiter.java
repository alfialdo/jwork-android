package alfialdo.jwork_android.object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class untuk membuat object Recruiter yang akan menyimpan data sementara
 * @author Muhammad Alfi A
 * @version Final Project - 20 June 2021
 */
public class Recruiter implements Parcelable
{
    private int id;
    private String name;
    private String email;
    private String phoneNumber;
    private Location location;

    public Recruiter(int id, String name, String email, String phoneNumber, Location location)
    {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.location = location;
    }

    protected Recruiter(Parcel in)
    {
        id = in.readInt();
        name = in.readString();
        email = in.readString();
        phoneNumber = in.readString();
        location = in.readParcelable(Location.class.getClassLoader());

    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(phoneNumber);
        dest.writeParcelable(location, flags);
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    public static final Creator<Recruiter> CREATOR = new Creator<Recruiter>()
    {
        @Override
        public Recruiter createFromParcel(Parcel in)
        {
            return new Recruiter(in);
        }

        @Override
        public Recruiter[] newArray(int size)
        {
            return new Recruiter[size];
        }
    };

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public Location getLocation()
    {
        return location;
    }

    public void setLocation(Location location)
    {
        this.location = location;
    }
}
