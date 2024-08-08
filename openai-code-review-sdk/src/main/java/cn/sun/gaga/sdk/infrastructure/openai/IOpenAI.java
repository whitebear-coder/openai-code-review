package cn.sun.gaga.sdk.infrastructure.openai;

import cn.sun.gaga.sdk.infrastructure.openai.dto.ChatCompletionRequestDTO;
import cn.sun.gaga.sdk.infrastructure.openai.dto.ChatCompletionSyncResponseDTO;

public interface IOpenAI {

    ChatCompletionSyncResponseDTO completions(ChatCompletionRequestDTO requestDTO) throws Exception;

}
