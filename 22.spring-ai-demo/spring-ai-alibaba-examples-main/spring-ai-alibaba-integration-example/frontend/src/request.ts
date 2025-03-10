const BASE_URL = "/api/v1";

const decoder = new TextDecoder("utf-8");

export const getModels = async () => {
  const res = (await fetch(BASE_URL + "/dashscope/getModels", {
    method: "GET"
  })) as any;

  const reader = res.body.getReader();

  let bufffer = "";
  await reader.read().then(function process({ done, value }) {
    if (done) return;

    bufffer += decoder.decode(value);

    return reader.read().then(process);
  });

  return JSON.parse(bufffer).data;
};

export const getChat = async (
  message: string,
  callback: (value: Uint8Array) => void,
  params: {
    image?: File;
    model?: string;
    chatId?: string;
  }
) => {
  const { image, model, chatId } = params;
  let res: any;
  if (image === undefined) {
    res = (await fetch(BASE_URL + "/chat?prompt=" + message, {
      method: "GET",
      headers: {
        model: model ? model : "",
        chatId: chatId ? chatId : ""
      }
    })) as any;
  } else {
    const formData = new FormData();
    formData.append("prompt", message || "");
    formData.append("image", image);
    res = (await fetch(BASE_URL + "/image2text", {
      method: "POST",
      body: formData
    })) as any;
  }

  const reader = res.body.getReader();
  await reader.read().then(function process({ done, value }) {
    if (done) return;

    callback(value);

    return reader.read().then(process);
  });

  return res;
};
