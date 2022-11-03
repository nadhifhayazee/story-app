package com.nadhif.hayazee.model.dummies

import com.nadhif.hayazee.common.util.GsonUtil
import com.nadhif.hayazee.model.common.Story
import com.nadhif.hayazee.model.response.GetStoryResponse
import com.nadhif.hayazee.model.response.RegisterResponse

object HomeDummy {
    const val storyErrorDummy = "{\n" +
            "  \"error\": true,\n" +
            "  \"message\": \"Token already expired\"\n" +
            "}"

    private const val storyList = "{\n" +
            "    \"error\": false,\n" +
            "    \"message\": \"Stories fetched successfully\",\n" +
            "    \"listStory\": [\n" +
            "        {\n" +
            "            \"id\": \"story-HorXO_6eAtdopNq1\",\n" +
            "            \"name\": \"Reza Cahyono\",\n" +
            "            \"description\": \"Aku adalah seorang kepala kotak hijau.  Ingin membasmi boboby di muka bumi.\",\n" +
            "            \"photoUrl\": \"https://story-api.dicoding.dev/images/stories/photos-1667289424862_9uxO4ThW.jpg\",\n" +
            "            \"createdAt\": \"2022-11-01T07:57:04.863Z\",\n" +
            "            \"lat\": null,\n" +
            "            \"lon\": null\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"story-nEJcvcJvHGYXS86W\",\n" +
            "            \"name\": \"ADMIN STORY\",\n" +
            "            \"description\": \"dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged\",\n" +
            "            \"photoUrl\": \"https://story-api.dicoding.dev/images/stories/photos-1667289421237_LbaqYeCl.jpg\",\n" +
            "            \"createdAt\": \"2022-11-01T07:57:01.240Z\",\n" +
            "            \"lat\": null,\n" +
            "            \"lon\": null\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"story-OEOd4w6BiW3iizB2\",\n" +
            "            \"name\": \"ADMIN STORY\",\n" +
            "            \"description\": \"dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged\",\n" +
            "            \"photoUrl\": \"https://story-api.dicoding.dev/images/stories/photos-1667289384422_uIjd7YE5.jpg\",\n" +
            "            \"createdAt\": \"2022-11-01T07:56:24.424Z\",\n" +
            "            \"lat\": null,\n" +
            "            \"lon\": null\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"story-xtPW-NeBDHR3-l7m\",\n" +
            "            \"name\": \"ajeng\",\n" +
            "            \"description\": \"test moga berhasil\",\n" +
            "            \"photoUrl\": \"https://story-api.dicoding.dev/images/stories/photos-1667289050261_TtXyFLJI.jpg\",\n" +
            "            \"createdAt\": \"2022-11-01T07:50:50.262Z\",\n" +
            "            \"lat\": -2.9736919,\n" +
            "            \"lon\": 104.7642212\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"story-Nr4-Mh16dW2HzrV5\",\n" +
            "            \"name\": \"Ipung Hendi Munandar\",\n" +
            "            \"description\": \"testing\",\n" +
            "            \"photoUrl\": \"https://story-api.dicoding.dev/images/stories/photos-1667287491607_KbXSBahm.jpg\",\n" +
            "            \"createdAt\": \"2022-11-01T07:24:51.610Z\",\n" +
            "            \"lat\": null,\n" +
            "            \"lon\": null\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"story-Z58Lvfb0TJqxKGLE\",\n" +
            "            \"name\": \"MF\",\n" +
            "            \"description\": \"ts\",\n" +
            "            \"photoUrl\": \"https://story-api.dicoding.dev/images/stories/photos-1667287327778_X5Pq1_x2.jpg\",\n" +
            "            \"createdAt\": \"2022-11-01T07:22:07.781Z\",\n" +
            "            \"lat\": null,\n" +
            "            \"lon\": null\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"story-PjMpwc_-MSbdQB4U\",\n" +
            "            \"name\": \"Tri 2\",\n" +
            "            \"description\": \"nasi padang\",\n" +
            "            \"photoUrl\": \"https://story-api.dicoding.dev/images/stories/photos-1667287274786_TXmAMMYA.jpg\",\n" +
            "            \"createdAt\": \"2022-11-01T07:21:14.791Z\",\n" +
            "            \"lat\": -6.8802161,\n" +
            "            \"lon\": 107.5871863\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"story-IhUL22A5stKIYyJZ\",\n" +
            "            \"name\": \"PROAAI41\",\n" +
            "            \"description\": \"tes\",\n" +
            "            \"photoUrl\": \"https://story-api.dicoding.dev/images/stories/photos-1667286995107_VDCWDv1A.jpg\",\n" +
            "            \"createdAt\": \"2022-11-01T07:16:35.110Z\",\n" +
            "            \"lat\": -4.0897726,\n" +
            "            \"lon\": 104.4681448\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"story-37CZTcnVo42jMy8C\",\n" +
            "            \"name\": \"Lim\",\n" +
            "            \"description\": \"hdh\",\n" +
            "            \"photoUrl\": \"https://story-api.dicoding.dev/images/stories/photos-1667286839141_OYv-5hkp.jpg\",\n" +
            "            \"createdAt\": \"2022-11-01T07:13:59.143Z\",\n" +
            "            \"lat\": null,\n" +
            "            \"lon\": null\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"story-7F-DPi88Gstk0bai\",\n" +
            "            \"name\": \"Lim\",\n" +
            "            \"description\": \"jsjs\",\n" +
            "            \"photoUrl\": \"https://story-api.dicoding.dev/images/stories/photos-1667286770765_njpaBKfr.jpg\",\n" +
            "            \"createdAt\": \"2022-11-01T07:12:50.767Z\",\n" +
            "            \"lat\": null,\n" +
            "            \"lon\": null\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"story-3JqC55JRGivAl5Vs\",\n" +
            "            \"name\": \"reviewer123\",\n" +
            "            \"description\": \"rferferferferf\",\n" +
            "            \"photoUrl\": \"https://story-api.dicoding.dev/images/stories/photos-1667286747358_EWUnyCug.image\",\n" +
            "            \"createdAt\": \"2022-11-01T07:12:27.359Z\",\n" +
            "            \"lat\": null,\n" +
            "            \"lon\": null\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"story-P_v_215r7i8isgZf\",\n" +
            "            \"name\": \"Lim\",\n" +
            "            \"description\": \"dari gallery\",\n" +
            "            \"photoUrl\": \"https://story-api.dicoding.dev/images/stories/photos-1667286716635_QNoyH3Jw.jpg\",\n" +
            "            \"createdAt\": \"2022-11-01T07:11:56.636Z\",\n" +
            "            \"lat\": null,\n" +
            "            \"lon\": null\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"story-xnANw5nHnyKm_Eja\",\n" +
            "            \"name\": \"uokli\",\n" +
            "            \"description\": \"Asdsadsadasdsaa\",\n" +
            "            \"photoUrl\": \"https://story-api.dicoding.dev/images/stories/photos-1667286065236_a6Pkw8Gd.jpg\",\n" +
            "            \"createdAt\": \"2022-11-01T07:01:05.237Z\",\n" +
            "            \"lat\": null,\n" +
            "            \"lon\": null\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"story-K_MIPABhXcyfgZHZ\",\n" +
            "            \"name\": \"uokli\",\n" +
            "            \"description\": \"Sddaasdsad\",\n" +
            "            \"photoUrl\": \"https://story-api.dicoding.dev/images/stories/photos-1667286044851_jqGTdazr.jpg\",\n" +
            "            \"createdAt\": \"2022-11-01T07:00:44.853Z\",\n" +
            "            \"lat\": null,\n" +
            "            \"lon\": null\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"story-P9rVVLmGbqLRUY8h\",\n" +
            "            \"name\": \"Rev\",\n" +
            "            \"description\": \"test\",\n" +
            "            \"photoUrl\": \"https://story-api.dicoding.dev/images/stories/photos-1667285984979_pL7G_J0e.jpg\",\n" +
            "            \"createdAt\": \"2022-11-01T06:59:44.980Z\",\n" +
            "            \"lat\": null,\n" +
            "            \"lon\": null\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"story-Ql7vKyDPYkR5gLeC\",\n" +
            "            \"name\": \"doneregister\",\n" +
            "            \"description\": \"testtttttttttt\",\n" +
            "            \"photoUrl\": \"https://story-api.dicoding.dev/images/stories/photos-1667284789683_lOYg-ncN.jpg\",\n" +
            "            \"createdAt\": \"2022-11-01T06:39:49.684Z\",\n" +
            "            \"lat\": null,\n" +
            "            \"lon\": null\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"story-LzEbN43covGEIt7X\",\n" +
            "            \"name\": \"doneregister\",\n" +
            "            \"description\": \"asdasdasdasdxxxxx\",\n" +
            "            \"photoUrl\": \"https://story-api.dicoding.dev/images/stories/photos-1667284493119_51YG3ND_.jpg\",\n" +
            "            \"createdAt\": \"2022-11-01T06:34:53.122Z\",\n" +
            "            \"lat\": null,\n" +
            "            \"lon\": null\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"story-mRKldNCm0kbs4352\",\n" +
            "            \"name\": \"doneregister\",\n" +
            "            \"description\": \"atesetsetsetsesdsdf\",\n" +
            "            \"photoUrl\": \"https://story-api.dicoding.dev/images/stories/photos-1667284035459_Y9Fevd1v.jpg\",\n" +
            "            \"createdAt\": \"2022-11-01T06:27:15.462Z\",\n" +
            "            \"lat\": null,\n" +
            "            \"lon\": null\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"story-4Ao7gXo8IETqnHO5\",\n" +
            "            \"name\": \"reviewer123\",\n" +
            "            \"description\": \"Efefefefefe\",\n" +
            "            \"photoUrl\": \"https://story-api.dicoding.dev/images/stories/photos-1667283903872_uWIKA_Sd.jpg\",\n" +
            "            \"createdAt\": \"2022-11-01T06:25:03.874Z\",\n" +
            "            \"lat\": null,\n" +
            "            \"lon\": null\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": \"story-q3fhFo4SuwWh4Q6-\",\n" +
            "            \"name\": \"reviewer123\",\n" +
            "            \"description\": \"Fghrghrthrhrth\",\n" +
            "            \"photoUrl\": \"https://story-api.dicoding.dev/images/stories/photos-1667283892535__ZooUE3u.jpg\",\n" +
            "            \"createdAt\": \"2022-11-01T06:24:52.537Z\",\n" +
            "            \"lat\": null,\n" +
            "            \"lon\": null\n" +
            "        }\n" +
            "    ]\n" +
            "}"

    fun getDummyStoryList(): List<Story>? {
        val response = GsonUtil.fromJson(storyList, GetStoryResponse::class.java)
        return response?.listStory
    }

    fun getDummyStoryResponse(): GetStoryResponse? {
        return GsonUtil.fromJson(storyList, GetStoryResponse::class.java)
    }

    fun getDummyPostStoryResponse(): RegisterResponse? {
        val json = "{\n" +
                "  \"error\": false,\n" +
                "  \"message\": \"Story created\"\n" +
                "}"
        return GsonUtil.fromJson(json, RegisterResponse::class.java)
    }

}