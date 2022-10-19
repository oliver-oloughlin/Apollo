package enums;

import com.google.gson.annotations.SerializedName;

public enum AccountType {
  @SerializedName("Normal")
  NORMAL,
  @SerializedName("Facebook")
  FACEBOOK,
  @SerializedName("Google")
  GOOGLE;
}
