/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package ink.whi.video.controller;

import com.qiniu.storage.model.DefaultPutRet;
import ink.whi.common.vo.ResVo;
import ink.whi.common.vo.page.PageListVo;
import ink.whi.common.vo.page.PageParam;
import ink.whi.video.model.dto.QiniuQueryCriteria;
import ink.whi.video.repo.qiniu.entity.QiniuConfig;
import ink.whi.video.repo.qiniu.entity.QiniuContent;
import ink.whi.video.service.QiNiuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 七牛云存储管理
 *
 * @author qing
 * @date 2023/10/25
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/qiniu")
public class QiniuController {

    private final QiNiuService qiNiuService;

    /**
     * 获取配置
     * @return
     */
    @GetMapping(value = "config")
    public ResVo<QiniuConfig> queryQiNiuConfig() {
        QiniuConfig config = qiNiuService.getConfig();
        return ResVo.ok(config);
    }

    /**
     * 设置七牛云存储
     *
     * @param qiniuConfig
     * @return
     */
    @PutMapping(value = "config")
    public ResVo<String> updateQiNiuConfig(@RequestBody QiniuConfig qiniuConfig) {
        qiNiuService.setConfig(qiniuConfig);
        return ResVo.ok("ok");
    }

    /**
     * 导出数据
     * @param response
     * @param criteria
     * @throws IOException
     */
//    @GetMapping(value = "/download")
//    public void exportQiNiu(HttpServletResponse response, QiniuQueryCriteria criteria) throws IOException {
//        qiNiuService.downloadList(qiNiuService.queryAll(criteria), response);
//    }

    /**
     * 获取视频列表
     *
     * @param criteria
     * @param pageParam
     * @return
     */
    @GetMapping("video")
    public ResVo<PageListVo<QiniuContent>> queryQiNiu(QiniuQueryCriteria criteria, PageParam pageParam) {
        return ResVo.ok(qiNiuService.queryFiles(criteria, pageParam));
    }

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @PostMapping
    public ResponseEntity<Object> uploadQiNiu(@RequestParam MultipartFile file) throws IOException {
        DefaultPutRet putKey = qiNiuService.upload(file);
        Map<String, Object> map = new HashMap<>(2);
        map.put("url", putKey.key);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /**
     * 同步七牛云数据
     *
     * @return
     */
    @PostMapping(value = "/synchronize")
    public ResponseEntity<Object> synchronizeQiNiu() {
//        qiNiuService.synchronize(qiNiuService.find());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 下载文件
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/download/{id}")
    public ResponseEntity<Object> downloadQiNiu(@PathVariable Long id) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("url", qiNiuService.download(qiNiuService.queryContentById(id), qiNiuService.getConfig()));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /**
     * 删除文件
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteQiNiu(@PathVariable Long id) {
//        qiNiuService.delete(qiNiuService.findByContentId(id), qiNiuService.find());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 删除多张图片
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    public ResponseEntity<Object> deleteAllQiNiu(@RequestBody Long[] ids) {
//        qiNiuService.deleteAll(ids, qiNiuService.find());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
