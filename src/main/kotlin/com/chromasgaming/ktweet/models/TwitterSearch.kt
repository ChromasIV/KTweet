import com.chromasgaming.ktweet.models.TweetObject
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TwitterSearch(
    @SerialName("data") val tweets: List<TweetObject>,
    @SerialName("meta") val meta: Meta
)

@Serializable
data class Meta(
    @SerialName("newest_id") val newestId: String,
    @SerialName("oldest_id") val oldestId: String,
    @SerialName("result_count") val resultCount: Int
)
