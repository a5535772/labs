def simple_generator(input_dict):
    """一个简单的生成器，用于检测并返回一个字典中的变化"""
    # 记录初始状态（创建快照）
    initial_countries = input_dict.get("countries", []).copy()  # 创建初始状态的副本

    # 如果初始时countries为空，提示用户
    if not initial_countries:
        yield "初始countries为空"

    try:
        # 检测字典变化的循环
        while True:
            # 检查countries键是否存在
            if "countries" not in input_dict:
                yield "字典结构发生变化，countries键被删除"
                continue

            current_countries = input_dict["countries"]

            # 检查是否添加了国家
            if len(current_countries) > len(initial_countries):
                new_country = current_countries[-1]
                yield f"新增国家：{new_country.get('name', '未知')}，当前共有{len(current_countries)}个国家"
            else:
                # 如果字典没有变化，提示用户
                yield "字典未发生变化，等待更新..."
    except Exception as e:
        yield f"发生异常：{str(e)}"


# 创建一个dict
input_dict = {
    "countries": [
    ]
}

gen = simple_generator(input_dict)
print(next(gen))

# 循环3次
for i in range(3):
    # i==1的时候添加中国
    if i == 0:
        input_dict["countries"].append({"name": "China"})
    # i==1的时候添加印度
    if i == 1:
        input_dict["countries"].append({"name": "India"})
    # i==2的时候添加美国
    if i == 2:
        input_dict["countries"].append({"name": "USA"})

    print(next(gen))  # 每次更新后获取生成的器输出