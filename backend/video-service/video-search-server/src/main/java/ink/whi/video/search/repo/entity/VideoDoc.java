package ink.whi.video.search.repo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * Video 文档类型
 *
 * @author qing
 * @date 2023/11/4
 */
@Data
@Document(indexName = "video")
public class VideoDoc {

	/**
	 * 业务id
	 */
	@Id
	Integer id;

	/**
	 * 作者id
	 */
	@Field(type = FieldType.Integer)
	Integer authorId;

	@Field(type = FieldType.Text, index = false)
	String url;

	/**
	 * 标题
	 */
	@Field(type = FieldType.Text, analyzer = "ik_max_word", copyTo = "descriptiveContent")
	String title;

	/**
	 * 描述
	 */
	@Field(type = FieldType.Text, analyzer = "ik_max_word", copyTo = "descriptiveContent")
	String thumbnail;

	/**
	 * 视频封面
	 */
	@Field(type = FieldType.Keyword, index = false)
	String picture;

	/**
	 * 状态：0-未发布,1-已发布,2-待审核
	 */
	@Field(type = FieldType.Integer, index = false)
	Integer status;

	/**
	 * 用于搜索功能
	 */
	@JsonIgnore
	@Field(type = FieldType.Text, analyzer = "ik_max_word", ignoreFields = "descriptiveContent", excludeFromSource = true)
	String descriptiveContent;


	/**
	 * 创建时间
	 */
	@Field(type = FieldType.Date, pattern = "uuuu-MM-dd HH:mm:ss")
	Date createTime;

	/**
	 * 修改时间
	 */
	@Field(type = FieldType.Date, pattern = "uuuu-MM-dd HH:mm:ss")
	Date updateTime;
}
