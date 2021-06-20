package alfialdo.jwork_android.object;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

/**
 * Class untuk membuat object Bonus yang akan menyimpan data sementara
 * @author Muhammad Alfi A
 * @version Final Project - 20 June 2021
 */
public class Bonus implements Parcelable
{
    private int id, extraFee, minTotalFee;
    private String referralCode;
    private Boolean active;

    public Bonus(int id, int extraFee, int minTotalFee, String referralCode, Boolean active)
    {
        this.id = id;
        this.extraFee = extraFee;
        this.minTotalFee = minTotalFee;
        this.referralCode = referralCode;
        this.active = active;
    }

    protected Bonus(Parcel in)
    {
        id = in.readInt();
        extraFee = in.readInt();
        minTotalFee = in.readInt();
        referralCode = in.readString();
        active = in.readInt() != 0;

    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(id);
        dest.writeInt(extraFee);
        dest.writeInt(minTotalFee);
        dest.writeString(referralCode);
        dest.writeInt(active? 1 : 0);
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    public static final Creator<Bonus> CREATOR = new Creator<Bonus>()
    {
        @Override
        public Bonus createFromParcel(Parcel in)
        {
            return new Bonus(in);
        }

        @Override
        public Bonus[] newArray(int size)
        {
            return new Bonus[size];
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

    public int getExtraFee()
    {
        return extraFee;
    }

    public void setExtraFee(int extraFee)
    {
        this.extraFee = extraFee;
    }

    public int getMinTotalFee()
    {
        return minTotalFee;
    }

    public void setMinTotalFee(int minTotalFee)
    {
        this.minTotalFee = minTotalFee;
    }

    public String getReferralCode()
    {
        return referralCode;
    }

    public void setReferralCode(String referralCode)
    {
        this.referralCode = referralCode;
    }

    public Boolean getActive()
    {
        return active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
    }

}
