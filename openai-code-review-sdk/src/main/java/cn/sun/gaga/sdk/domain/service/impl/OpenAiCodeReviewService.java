package cn.sun.gaga.sdk.domain.service.impl;

import cn.sun.gaga.sdk.domain.service.AbstractOpenAiCodeReviewService;
import cn.sun.gaga.sdk.infrastructure.git.GitCommand;
import cn.sun.gaga.sdk.infrastructure.openai.IOpenAI;
import cn.sun.gaga.sdk.infrastructure.weixin.WeiXin;
import cn.sun.gaga.sdk.infrastructure.weixin.dto.TemplateMessageDTO;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class OpenAiCodeReviewService extends AbstractOpenAiCodeReviewService {

    public OpenAiCodeReviewService(GitCommand gitCommand, IOpenAI openAI, WeiXin weiXin) {
        super(gitCommand, openAI, weiXin);
    }


    @Override
    protected String getDiffCode() throws IOException, InterruptedException {
        return gitCommand.diff();
    }



    @Override
    protected String codeReview(String diffCode) throws Exception {
        return "";
    }

    @Override
    protected String recordCodeReview(String recommend) throws Exception {
        return gitCommand.commitAndPush(recommend);
    }


    @Override
    protected void pushMessage(String logUrl) throws Exception {
        Map<String, Map<String, String>> data = new HashMap<>();
        TemplateMessageDTO.put(data, TemplateMessageDTO.TemplateKey.REPO_NAME, gitCommand.getProject());
        TemplateMessageDTO.put(data, TemplateMessageDTO.TemplateKey.BRANCH_NAME, gitCommand.getBranch());
        TemplateMessageDTO.put(data, TemplateMessageDTO.TemplateKey.COMMIT_AUTHOR, gitCommand.getAuthor());
        TemplateMessageDTO.put(data, TemplateMessageDTO.TemplateKey.COMMIT_MESSAGE, gitCommand.getMessage());
        weiXin.sendTemplateMessage(logUrl, data);
    }

}
