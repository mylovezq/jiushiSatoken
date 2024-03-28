local productJson = redis.call('GET', KEYS[1])
if not productJson then
    return '商品已被秒杀完'
end
-- 获取当前Unix时间戳（以秒为单位）
local currentTime = tonumber(redis.call('TIME')[1])
local product = cjson.decode(productJson)

local startDateTime = tonumber(product.startDateTimeUnix)
local endDateTime = tonumber(product.endDateTimeUnix)
if currentTime < startDateTime then
    return '秒杀活动未开始'
end
if endDateTime < currentTime then
    return '秒杀活动已结束'
end

local userLimitBuyOldCount = redis.call('GET', KEYS[2])
local userLimitBuyRealOldTtl = redis.call('TTL', KEYS[2])

local userLimitBuyRealCount = (userLimitBuyOldCount == nil) and 0 or (tonumber(userLimitBuyOldCount) or 0)
if userLimitBuyRealCount + tonumber(ARGV[1]) > tonumber(product.limitBuy) then
    return '你的账号限购' .. tostring(product.limitBuy) ..'个'
end

if not (product and product.stockCount and product.stockCount >= tonumber(ARGV[1])) then
    return '商品已被秒杀完'
end

product.stockCount = product.stockCount - tonumber(ARGV[1])

local productTtl = redis.call('TTL', KEYS[1])
local userLimitBuyRealTtl = (userLimitBuyRealOldTtl == -1 or -2) and 21600 or (tonumber(userLimitBuyRealOldTtl) or 0)

redis.call('SET', KEYS[1], cjson.encode(product),'EX',productTtl)
redis.call('SET', KEYS[2], userLimitBuyRealCount + tonumber(ARGV[1]),'EX',userLimitBuyRealTtl)
return 'success'