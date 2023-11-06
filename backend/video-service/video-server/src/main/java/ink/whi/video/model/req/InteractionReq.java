package ink.whi.video.model.req;

import lombok.Data;

import java.io.Serializable;

@Data
public class InteractionReq implements Serializable {
    public Long videoId;
    public Integer type;
    public Integer data;
}
