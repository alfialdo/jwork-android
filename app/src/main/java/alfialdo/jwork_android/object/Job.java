package alfialdo.jwork_android.object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class untuk membuat object Job yang akan menyimpan data sementara
 * @author Muhammad Alfi A
 * @version Final Project - 20 June 2021
 */
public class Job implements Parcelable
{
    private int id;
    private String name;
    private Recruiter recruiter;
    private int fee;
    private String category;

    public Job(int id, String name, Recruiter recruiter, int fee, String category)
    {
        this.id = id;
        this.name = name;
        this.recruiter = recruiter;
        this.fee = fee;
        this.category = category;
    }

    protected Job(Parcel in)
    {
        id = in.readInt();
        name = in.readString();
        fee = in.readInt();
        category = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(fee);
        dest.writeString(category);
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    public static final Creator<Job> CREATOR = new Creator<Job>()
    {
        @Override
        public Job createFromParcel(Parcel in)
        {
            return new Job(in);
        }

        @Override
        public Job[] newArray(int size)
        {
            return new Job[size];
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

    public Recruiter getRecruiter()
    {
        return recruiter;
    }

    public void setRecruiter(Recruiter recruiter)
    {
        this.recruiter = recruiter;
    }

    public int getFee()
    {
        return fee;
    }

    public void setFee(int fee)
    {
        this.fee = fee;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }


}
