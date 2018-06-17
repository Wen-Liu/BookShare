package com.wenliu.bookshare.api;

import org.junit.Test;

import static org.junit.Assert.*;

public class ShareBookParserTest {

    @Test
    public void parseBookDetailUrl() {
        String jasonString = "{\n" +
                " \"kind\": \"books#volumes\",\n" +
                " \"totalItems\": 1,\n" +
                " \"items\": [\n" +
                "  {\n" +
                "   \"kind\": \"books#volume\",\n" +
                "   \"id\": \"D4EzCgAAQBAJ\",\n" +
                "   \"etag\": \"2VQ2C4950Gc\",\n" +
                "   \"selfLink\": \"https://www.googleapis.com/books/v1/volumes/D4EzCgAAQBAJ\",\n" +
                "   \"volumeInfo\": {\n" +
                "    \"title\": \"閱讀的力量\",\n" +
                "    \"subtitle\": \"改變生命的十趟閱讀之旅\",\n" +
                "    \"authors\": [\n" +
                "     \"吳錦勳\",\n" +
                "     \"李桂芬\",\n" +
                "     \"李康莉\",\n" +
                "     \"林宜諄\",\n" +
                "     \"彭蕙仙\",\n" +
                "     \"謝其濬\"\n" +
                "    ],\n" +
                "    \"publisher\": \"遠見天下文化出版股份有限公司\",\n" +
                "    \"publishedDate\": \"2013-09-30\",\n" +
                "    \"description\": \"►十九世紀哲學家培根說：「知識就是力量。」但他沒說的是，唯有經由閱讀，知識才能變成力量。 ►閱讀，不僅是一種愉悅，更是一種理解、思考與判斷的能力，讓自己可以擺脫平庸與急功近利的謀生態度。 ►十種閱讀樣貌，十幅人生風景，淘選出上百本珍藏經典，匯聚成一股股智慧之泉 這些名字對社會大眾來說應該都不陌生： ◎李家同，以宗教家般的情懷，始終關注臺灣青年人觀念的清華大學教授。 ◎陶傳正，熱中音樂、演戲、拍照、旅遊的奇哥董事長。 ◎嚴長壽，自飯店管理領域光榮交棒，轉而關心台灣社會教育各層面的公益平台文化基金會董事長。 ◎陳怡蓁，穿梭於科技與文學之間的趨勢教育基金會董事長。 ◎蘇國垚，希望將在亞都麗緻總經理任職期間的寶貴實務經驗，傳承給有志餐飲管理莘莘學子的現任高雄餐旅大學助理教授。 ◎林天來，從花蓮女中圖書館管理員，認真努力到成為出版社高層的遠見天下文化事業群副社長。 ◎沈方正，從「人」和「文化」角度出發的老爺大酒店集團總執行長。 ◎劉軒，在作家父親劉墉盛名之下成長，另闖出自己一片天的知名DJ及作家。 ◎幸佳慧，擁有俠女性格的兒童文學作家。 ◎郭國榮，曾是窮到連鬼都怕的金山窮小子，靠著苦幹實幹、大量閱讀，而成為「關渡土地公」的喬大文化基金會董事長。 這十位於公益、教育、藝文、出版、服務各界的菁英，無論是自艱困環境中力爭上游，或脫胎於家學淵源另闢擅場，成為各領域翹楚，其中最大的共同點就是：閱讀。 在本書中，他們不僅分享各自不同的人生歷程，與如何從閱讀中汲取生命能量的體會，並於篇末羅列影響自己最深或最想推薦的各類經典。與這些傑出人士成功相關的百餘本作品，就像書海中精選出的珍珠，絕對值得讀者玩味再三；在他們的導引下，讀者一定也能從中發現閱讀的力量。\",\n" +
                "    \"industryIdentifiers\": [\n" +
                "     {\n" +
                "      \"type\": \"ISBN_13\",\n" +
                "      \"identifier\": \"9789863202875\"\n" +
                "     },\n" +
                "     {\n" +
                "      \"type\": \"ISBN_10\",\n" +
                "      \"identifier\": \"9863202878\"\n" +
                "     }\n" +
                "    ],\n" +
                "    \"readingModes\": {\n" +
                "     \"text\": true,\n" +
                "     \"image\": true\n" +
                "    },\n" +
                "    \"pageCount\": 240,\n" +
                "    \"printType\": \"BOOK\",\n" +
                "    \"categories\": [\n" +
                "     \"Social Science\"\n" +
                "    ],\n" +
                "    \"maturityRating\": \"NOT_MATURE\",\n" +
                "    \"allowAnonLogging\": false,\n" +
                "    \"contentVersion\": \"0.1.1.0.preview.3\",\n" +
                "    \"panelizationSummary\": {\n" +
                "     \"containsEpubBubbles\": false,\n" +
                "     \"containsImageBubbles\": false\n" +
                "    },\n" +
                "    \"imageLinks\": {\n" +
                "     \"smallThumbnail\": \"http://books.google.com/books/content?id=D4EzCgAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api\",\n" +
                "     \"thumbnail\": \"http://books.google.com/books/content?id=D4EzCgAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\"\n" +
                "    },\n" +
                "    \"language\": \"zh-TW\",\n" +
                "    \"previewLink\": \"http://books.google.com.tw/books?id=D4EzCgAAQBAJ&printsec=frontcover&dq=isbn:9789863202875&hl=&cd=1&source=gbs_api\",\n" +
                "    \"infoLink\": \"https://play.google.com/store/books/details?id=D4EzCgAAQBAJ&source=gbs_api\",\n" +
                "    \"canonicalVolumeLink\": \"https://market.android.com/details?id=book-D4EzCgAAQBAJ\"\n" +
                "   },\n" +
                "   \"saleInfo\": {\n" +
                "    \"country\": \"TW\",\n" +
                "    \"saleability\": \"FOR_SALE\",\n" +
                "    \"isEbook\": true,\n" +
                "    \"listPrice\": {\n" +
                "     \"amount\": 236.0,\n" +
                "     \"currencyCode\": \"TWD\"\n" +
                "    },\n" +
                "    \"retailPrice\": {\n" +
                "     \"amount\": 177.0,\n" +
                "     \"currencyCode\": \"TWD\"\n" +
                "    },\n" +
                "    \"buyLink\": \"https://play.google.com/store/books/details?id=D4EzCgAAQBAJ&rdid=book-D4EzCgAAQBAJ&rdot=1&source=gbs_api\",\n" +
                "    \"offers\": [\n" +
                "     {\n" +
                "      \"finskyOfferType\": 1,\n" +
                "      \"listPrice\": {\n" +
                "       \"amountInMicros\": 2.36E8,\n" +
                "       \"currencyCode\": \"TWD\"\n" +
                "      },\n" +
                "      \"retailPrice\": {\n" +
                "       \"amountInMicros\": 1.77E8,\n" +
                "       \"currencyCode\": \"TWD\"\n" +
                "      }\n" +
                "     }\n" +
                "    ]\n" +
                "   },\n" +
                "   \"accessInfo\": {\n" +
                "    \"country\": \"TW\",\n" +
                "    \"viewability\": \"PARTIAL\",\n" +
                "    \"embeddable\": true,\n" +
                "    \"publicDomain\": false,\n" +
                "    \"textToSpeechPermission\": \"ALLOWED\",\n" +
                "    \"epub\": {\n" +
                "     \"isAvailable\": true,\n" +
                "     \"acsTokenLink\": \"http://books.google.com.tw/books/download/%E9%96%B1%E8%AE%80%E7%9A%84%E5%8A%9B%E9%87%8F-sample-epub.acsm?id=D4EzCgAAQBAJ&format=epub&output=acs4_fulfillment_token&dl_type=sample&source=gbs_api\"\n" +
                "    },\n" +
                "    \"pdf\": {\n" +
                "     \"isAvailable\": true,\n" +
                "     \"acsTokenLink\": \"http://books.google.com.tw/books/download/%E9%96%B1%E8%AE%80%E7%9A%84%E5%8A%9B%E9%87%8F-sample-pdf.acsm?id=D4EzCgAAQBAJ&format=pdf&output=acs4_fulfillment_token&dl_type=sample&source=gbs_api\"\n" +
                "    },\n" +
                "    \"webReaderLink\": \"http://play.google.com/books/reader?id=D4EzCgAAQBAJ&hl=&printsec=frontcover&source=gbs_api\",\n" +
                "    \"accessViewStatus\": \"SAMPLE\",\n" +
                "    \"quoteSharingAllowed\": false\n" +
                "   },\n" +
                "   \"searchInfo\": {\n" +
                "    \"textSnippet\": \"►十九世紀哲學家培根說：「知識就是力量。」但他沒說的是，唯有經由閱讀，知識才能變成力量。 ...\"\n" +
                "   }\n" +
                "  }\n" +
                " ]\n" +
                "}";

        String url = ShareBookParser.parseBookDetailUrl(jasonString);
        assertEquals("https://www.googleapis.com/books/v1/volumes/D4EzCgAAQBAJ", url);
    }
}