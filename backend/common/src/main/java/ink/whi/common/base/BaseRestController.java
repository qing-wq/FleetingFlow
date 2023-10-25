package ink.whi.common.base;


import ink.whi.common.exception.BusinessException;
import ink.whi.common.exception.StatusEnum;
import ink.whi.common.vo.page.PageParam;

/**
 * @author: qing
 * @Date: 2023/10/24
 */
public class BaseRestController {

    public PageParam buildPageParam(Long page, Long size) {
        if (page == null) {
            throw BusinessException.newInstance(StatusEnum.ILLEGAL_ARGUMENTS_MIXED, "page=" + page);
        }
        if (page <= 0) {
            page = PageParam.DEFAULT_PAGE_NUM;
        }
        if (size == null || size > PageParam.DEFAULT_PAGE_SIZE) {
            size = PageParam.DEFAULT_PAGE_SIZE;
        }
        return PageParam.newPageInstance(page, size);
    }
}
