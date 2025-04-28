package com.example.actualtravellerkiviprojectui.api;

import com.example.actualtravellerkiviprojectui.dto.PostDTO;
import com.fasterxml.jackson.core.StreamReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class PostServiceMock implements PostService {
    private static final ObjectMapper objectMapper = JsonMapper.builder().enable(StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION).build();
    private static  List<PostDTO> postDTOList = new ArrayList<>();
    static {
        try{
            postDTOList = objectMapper.readValue("[\n" +
                    "    {\n" +
                    "      \"id\": 1,\n" +
                    "      \"likers\": [],\n" +
                    "      \"tags\": [\n" +
                    "        {\n" +
                    "          \"id\": 2,\n" +
                    "          \"name\": \"firstPost\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"id\": 1,\n" +
                    "          \"name\": \"hello\"\n" +
                    "        }\n" +
                    "      ],\n" +
                    "      \"images\": [\n" +
                    "        {\n" +
                    "          \"id\": \"1408582a8-2b15-4024-b2da-1cb76e799f6f\",\n" +
                    "          \"contentLength\": 0,\n" +
                    "          \"contentMimeType\": \"text/plain\"\n" +
                    "        }\n" +
                    "      ],\n" +
                    "      \"body\": \"Hello everyone!\\\\nThis is my first post in the app.\",\n" +
                    "      \"created\": \"2025-04-28\",\n" +
                    "      \"owner\": {\n" +
                    "        \"id\": 1,\n" +
                    "        \"firstName\": \"Jake\",\n" +
                    "        \"lastName\": \"Motta\",\n" +
                    "        \"email\": \"jakemotta@gmail.com\",\n" +
                    "        \"userType\": \"REGULAR_USER\",\n" +
                    "        \"registrationDate\": \"2025-04-28\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 2,\n" +
                    "      \"likers\": [],\n" +
                    "      \"tags\": [\n" +
                    "        {\n" +
                    "          \"id\": 4,\n" +
                    "          \"name\": \"Kızılay\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"id\": 3,\n" +
                    "          \"name\": \"CanımAnkaram\"\n" +
                    "        }\n" +
                    "      ],\n" +
                    "      \"images\": [],\n" +
                    "      \"body\": \"Oh my god! I didn't know that Kızılay was this beautiful.\",\n" +
                    "      \"created\": \"2025-04-28\",\n" +
                    "      \"owner\": {\n" +
                    "        \"id\": 1,\n" +
                    "        \"firstName\": \"Jake\",\n" +
                    "        \"lastName\": \"Motta\",\n" +
                    "        \"email\": \"jakemotta@gmail.com\",\n" +
                    "        \"userType\": \"REGULAR_USER\",\n" +
                    "        \"registrationDate\": \"2025-04-28\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 3,\n" +
                    "      \"likers\": [],\n" +
                    "      \"tags\": [\n" +
                    "        {\n" +
                    "          \"id\": 5,\n" +
                    "          \"name\": \"Anıtkabir\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"id\": 6,\n" +
                    "          \"name\": \"Atatürk\"\n" +
                    "        }\n" +
                    "      ],\n" +
                    "      \"images\": [],\n" +
                    "      \"body\": \"Standing in awe at Anıtkabir, a majestic memorial dedicated to Mustafa Kemal Atatürk. The grandeur and historical significance of this place are truly remarkable! \uD83C\uDDF9\uD83C\uDDF7\",\n" +
                    "      \"created\": \"2025-04-28\",\n" +
                    "      \"owner\": {\n" +
                    "        \"id\": 1,\n" +
                    "        \"firstName\": \"Jake\",\n" +
                    "        \"lastName\": \"Motta\",\n" +
                    "        \"email\": \"jakemotta@gmail.com\",\n" +
                    "        \"userType\": \"REGULAR_USER\",\n" +
                    "        \"registrationDate\": \"2025-04-28\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 4,\n" +
                    "      \"likers\": [],\n" +
                    "      \"tags\": [\n" +
                    "        {\n" +
                    "          \"id\": 7,\n" +
                    "          \"name\": \"AnadoluMedeniyetleri\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"id\": 8,\n" +
                    "          \"name\": \"Museum\"\n" +
                    "        }\n" +
                    "      ],\n" +
                    "      \"images\": [],\n" +
                    "      \"body\": \"Exploring the Museum of Anatolian Civilizations, a treasure trove of ancient artifacts and fascinating history. A must-visit for history buffs! \uD83C\uDFDB\uFE0F\",\n" +
                    "      \"created\": \"2025-04-28\",\n" +
                    "      \"owner\": {\n" +
                    "        \"id\": 2,\n" +
                    "        \"firstName\": \"Orhan\",\n" +
                    "        \"lastName\": \"Gezdirmeci\",\n" +
                    "        \"email\": \"orhangg@gmail.com\",\n" +
                    "        \"userType\": \"GUIDE_USER\",\n" +
                    "        \"registrationDate\": \"2025-04-28\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 5,\n" +
                    "      \"likers\": [],\n" +
                    "      \"tags\": [\n" +
                    "        {\n" +
                    "          \"id\": 9,\n" +
                    "          \"name\": \"AnkaraKalesi\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"id\": 10,\n" +
                    "          \"name\": \"Castle\"\n" +
                    "        }\n" +
                    "      ],\n" +
                    "      \"images\": [],\n" +
                    "      \"body\": \"Climbing up to Ankara Kalesi, a historic fortress with breathtaking views. Stepping into the past and admiring the city from above! \uD83C\uDFF0\",\n" +
                    "      \"created\": \"2025-04-28\",\n" +
                    "      \"owner\": {\n" +
                    "        \"id\": 2,\n" +
                    "        \"firstName\": \"Orhan\",\n" +
                    "        \"lastName\": \"Gezdirmeci\",\n" +
                    "        \"email\": \"orhangg@gmail.com\",\n" +
                    "        \"userType\": \"GUIDE_USER\",\n" +
                    "        \"registrationDate\": \"2025-04-28\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 6,\n" +
                    "      \"likers\": [],\n" +
                    "      \"tags\": [\n" +
                    "        {\n" +
                    "          \"id\": 12,\n" +
                    "          \"name\": \"Temple\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"id\": 11,\n" +
                    "          \"name\": \"AugustusTapınağı\"\n" +
                    "        }\n" +
                    "      ],\n" +
                    "      \"images\": [],\n" +
                    "      \"body\": \"Discovering the ancient Augustus Tapınağı, a hidden gem in Ankara. Roman architecture and inscriptions take you back in time! \uD83C\uDFDB\uFE0F\",\n" +
                    "      \"created\": \"2025-04-28\",\n" +
                    "      \"owner\": {\n" +
                    "        \"id\": 2,\n" +
                    "        \"firstName\": \"Orhan\",\n" +
                    "        \"lastName\": \"Gezdirmeci\",\n" +
                    "        \"email\": \"orhangg@gmail.com\",\n" +
                    "        \"userType\": \"GUIDE_USER\",\n" +
                    "        \"registrationDate\": \"2025-04-28\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 7,\n" +
                    "      \"likers\": [],\n" +
                    "      \"tags\": [\n" +
                    "        {\n" +
                    "          \"id\": 8,\n" +
                    "          \"name\": \"Museum\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"id\": 13,\n" +
                    "          \"name\": \"ErimtanMüzesi\"\n" +
                    "        }\n" +
                    "      ],\n" +
                    "      \"images\": [],\n" +
                    "      \"body\": \"Uncovering ancient treasures at Erimtan Arkeoloji ve Sanat Müzesi. From archaeological finds to stunning art, it's a cultural journey! \uD83C\uDFDB\uFE0F\",\n" +
                    "      \"created\": \"2025-04-28\",\n" +
                    "      \"owner\": {\n" +
                    "        \"id\": 3,\n" +
                    "        \"firstName\": \"Mustafa\",\n" +
                    "        \"lastName\": \"Korkusuz\",\n" +
                    "        \"email\": \"yilmazz2022@gemail.com\",\n" +
                    "        \"userType\": \"ADMIN\",\n" +
                    "        \"registrationDate\": \"2025-04-28\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 8,\n" +
                    "      \"likers\": [],\n" +
                    "      \"tags\": [\n" +
                    "        {\n" +
                    "          \"id\": 8,\n" +
                    "          \"name\": \"Museum\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"id\": 14,\n" +
                    "          \"name\": \"RahmiKoçMüzesi\"\n" +
                    "        }\n" +
                    "      ],\n" +
                    "      \"images\": [],\n" +
                    "      \"body\": \"Stepping into the fascinating world of Rahmi M. Koç Müzesi, Ankara's first industrial museum. From transportation to science, it's a captivating experience! \uD83D\uDE80\",\n" +
                    "      \"created\": \"2025-04-28\",\n" +
                    "      \"owner\": {\n" +
                    "        \"id\": 3,\n" +
                    "        \"firstName\": \"Mustafa\",\n" +
                    "        \"lastName\": \"Korkusuz\",\n" +
                    "        \"email\": \"yilmazz2022@gemail.com\",\n" +
                    "        \"userType\": \"ADMIN\",\n" +
                    "        \"registrationDate\": \"2025-04-28\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 9,\n" +
                    "      \"likers\": [],\n" +
                    "      \"tags\": [\n" +
                    "        {\n" +
                    "          \"id\": 15,\n" +
                    "          \"name\": \"Hamamönü\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"id\": 16,\n" +
                    "          \"name\": \"OldTown\"\n" +
                    "        }\n" +
                    "      ],\n" +
                    "      \"images\": [],\n" +
                    "      \"body\": \"Wandering through the enchanting Hamamönü, a historic neighborhood with Ottoman charm. Beautiful architecture, local shops, and a glimpse into the past! \uD83D\uDD4C\",\n" +
                    "      \"created\": \"2025-04-28\",\n" +
                    "      \"owner\": {\n" +
                    "        \"id\": 3,\n" +
                    "        \"firstName\": \"Mustafa\",\n" +
                    "        \"lastName\": \"Korkusuz\",\n" +
                    "        \"email\": \"yilmazz2022@gemail.com\",\n" +
                    "        \"userType\": \"ADMIN\",\n" +
                    "        \"registrationDate\": \"2025-04-28\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 10,\n" +
                    "      \"likers\": [],\n" +
                    "      \"tags\": [\n" +
                    "        {\n" +
                    "          \"id\": 17,\n" +
                    "          \"name\": \"AtatürkEvi\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"id\": 6,\n" +
                    "          \"name\": \"Atatürk\"\n" +
                    "        }\n" +
                    "      ],\n" +
                    "      \"images\": [],\n" +
                    "      \"body\": \"Stepping into Atatürk Evi, a replica of Atatürk's house in Thessaloniki. A journey through history and a tribute to the founder of modern Turkey! \uD83C\uDDF9\uD83C\uDDF7\",\n" +
                    "      \"created\": \"2025-04-28\",\n" +
                    "      \"owner\": {\n" +
                    "        \"id\": 4,\n" +
                    "        \"firstName\": \"Ozan\",\n" +
                    "        \"lastName\": \"Deluro\",\n" +
                    "        \"email\": \"yilmazz2022@gemail.com\",\n" +
                    "        \"userType\": \"GUIDE_USER\",\n" +
                    "        \"registrationDate\": \"2025-04-28\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 11,\n" +
                    "      \"likers\": [],\n" +
                    "      \"tags\": [\n" +
                    "        {\n" +
                    "          \"id\": 19,\n" +
                    "          \"name\": \"Farm\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"id\": 18,\n" +
                    "          \"name\": \"AtatürkOrmanÇiftliği\"\n" +
                    "        }\n" +
                    "      ],\n" +
                    "      \"images\": [],\n" +
                    "      \"body\": \"Exploring Atatürk Orman Çiftliği, a lush green space with historical significance. From the zoo to the Atatürk Evi Museum, it's a delightful experience! \uD83C\uDF33\",\n" +
                    "      \"created\": \"2025-04-28\",\n" +
                    "      \"owner\": {\n" +
                    "        \"id\": 4,\n" +
                    "        \"firstName\": \"Ozan\",\n" +
                    "        \"lastName\": \"Deluro\",\n" +
                    "        \"email\": \"yilmazz2022@gemail.com\",\n" +
                    "        \"userType\": \"GUIDE_USER\",\n" +
                    "        \"registrationDate\": \"2025-04-28\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 12,\n" +
                    "      \"likers\": [],\n" +
                    "      \"tags\": [\n" +
                    "        {\n" +
                    "          \"id\": 21,\n" +
                    "          \"name\": \"Mosque\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"id\": 20,\n" +
                    "          \"name\": \"AslanhaneCamii\"\n" +
                    "        }\n" +
                    "      ],\n" +
                    "      \"images\": [],\n" +
                    "      \"body\": \"Visiting the ancient Aslanhane Camii, one of Ankara's oldest mosques. Its 13th-century architecture and Seljuk decorations are truly mesmerizing! \uD83D\uDD4C\",\n" +
                    "      \"created\": \"2025-04-28\",\n" +
                    "      \"owner\": {\n" +
                    "        \"id\": 4,\n" +
                    "        \"firstName\": \"Ozan\",\n" +
                    "        \"lastName\": \"Deluro\",\n" +
                    "        \"email\": \"yilmazz2022@gemail.com\",\n" +
                    "        \"userType\": \"GUIDE_USER\",\n" +
                    "        \"registrationDate\": \"2025-04-28\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 13,\n" +
                    "      \"likers\": [],\n" +
                    "      \"tags\": [\n" +
                    "        {\n" +
                    "          \"id\": 8,\n" +
                    "          \"name\": \"Museum\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"id\": 22,\n" +
                    "          \"name\": \"TBMM\"\n" +
                    "        }\n" +
                    "      ],\n" +
                    "      \"images\": [],\n" +
                    "      \"body\": \"Stepping into the Second Grand National Assembly Building, now a museum. Reliving the early days of the Turkish Republic and its rich history! \uD83C\uDDF9\uD83C\uDDF7\",\n" +
                    "      \"created\": \"2025-04-28\",\n" +
                    "      \"owner\": {\n" +
                    "        \"id\": 5,\n" +
                    "        \"firstName\": \"Buro\",\n" +
                    "        \"lastName\": \"Yanami\",\n" +
                    "        \"email\": \"yilmazz2022@gemail.com\",\n" +
                    "        \"userType\": \"REGULAR_USER\",\n" +
                    "        \"registrationDate\": \"2025-04-28\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 14,\n" +
                    "      \"likers\": [],\n" +
                    "      \"tags\": [\n" +
                    "        {\n" +
                    "          \"id\": 23,\n" +
                    "          \"name\": \"HistoricalSite\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"id\": 22,\n" +
                    "          \"name\": \"TBMM\"\n" +
                    "        }\n" +
                    "      ],\n" +
                    "      \"images\": [],\n" +
                    "      \"body\": \"Exploring the First Grand National Assembly Building, a historic site in Ankara. A journey through the early years of the Turkish Republic! \uD83C\uDDF9\uD83C\uDDF7\",\n" +
                    "      \"created\": \"2025-04-28\",\n" +
                    "      \"owner\": {\n" +
                    "        \"id\": 5,\n" +
                    "        \"firstName\": \"Buro\",\n" +
                    "        \"lastName\": \"Yanami\",\n" +
                    "        \"email\": \"yilmazz2022@gemail.com\",\n" +
                    "        \"userType\": \"REGULAR_USER\",\n" +
                    "        \"registrationDate\": \"2025-04-28\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 15,\n" +
                    "      \"likers\": [],\n" +
                    "      \"tags\": [\n" +
                    "        {\n" +
                    "          \"id\": 24,\n" +
                    "          \"name\": \"PTTPulMüzesi\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"id\": 8,\n" +
                    "          \"name\": \"Museum\"\n" +
                    "        }\n" +
                    "      ],\n" +
                    "      \"images\": [],\n" +
                    "      \"body\": \"Discovering the PTT Pul Müzesi, a treasure trove of stamps from around the world. A philatelist's dream come true! \uD83C\uDF0F\",\n" +
                    "      \"created\": \"2025-04-28\",\n" +
                    "      \"owner\": {\n" +
                    "        \"id\": 5,\n" +
                    "        \"firstName\": \"Buro\",\n" +
                    "        \"lastName\": \"Yanami\",\n" +
                    "        \"email\": \"yilmazz2022@gemail.com\",\n" +
                    "        \"userType\": \"REGULAR_USER\",\n" +
                    "        \"registrationDate\": \"2025-04-28\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 16,\n" +
                    "      \"likers\": [],\n" +
                    "      \"tags\": [\n" +
                    "        {\n" +
                    "          \"id\": 8,\n" +
                    "          \"name\": \"Museum\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"id\": 25,\n" +
                    "          \"name\": \"İşBankasıMüzesi\"\n" +
                    "        }\n" +
                    "      ],\n" +
                    "      \"images\": [],\n" +
                    "      \"body\": \"Visiting the Türkiye İş Bankası Museum, a journey through the country's economic history. From historical artifacts to old bank safes, it's fascinating! \uD83D\uDCB0\",\n" +
                    "      \"created\": \"2025-04-28\",\n" +
                    "      \"owner\": {\n" +
                    "        \"id\": 6,\n" +
                    "        \"firstName\": \"Ozan\",\n" +
                    "        \"lastName\": \"Numan\",\n" +
                    "        \"email\": \"yilmazz2022@gemail.com\",\n" +
                    "        \"userType\": \"REGULAR_USER\",\n" +
                    "        \"registrationDate\": \"2025-04-28\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 17,\n" +
                    "      \"likers\": [],\n" +
                    "      \"tags\": [\n" +
                    "        {\n" +
                    "          \"id\": 26,\n" +
                    "          \"name\": \"AnkaraPalas\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"id\": 27,\n" +
                    "          \"name\": \"HistoricalBuilding\"\n" +
                    "        }\n" +
                    "      ],\n" +
                    "      \"images\": [],\n" +
                    "      \"body\": \"Stepping into Ankara Palas, a historic guesthouse with a rich past. Its architecture and interior design take you back to the early 20th century! \uD83C\uDFF0\",\n" +
                    "      \"created\": \"2025-04-28\",\n" +
                    "      \"owner\": {\n" +
                    "        \"id\": 6,\n" +
                    "        \"firstName\": \"Ozan\",\n" +
                    "        \"lastName\": \"Numan\",\n" +
                    "        \"email\": \"yilmazz2022@gemail.com\",\n" +
                    "        \"userType\": \"REGULAR_USER\",\n" +
                    "        \"registrationDate\": \"2025-04-28\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 18,\n" +
                    "      \"likers\": [],\n" +
                    "      \"tags\": [\n" +
                    "        {\n" +
                    "          \"id\": 8,\n" +
                    "          \"name\": \"Museum\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"id\": 28,\n" +
                    "          \"name\": \"MüzeEvliyagil\"\n" +
                    "        }\n" +
                    "      ],\n" +
                    "      \"images\": [],\n" +
                    "      \"body\": \"Exploring the contemporary art at Müze Evliyagil, Ankara's first modern and contemporary art museum. From sculptures to paintings, it's a feast for the eyes! \uD83C\uDFA8\",\n" +
                    "      \"created\": \"2025-04-28\",\n" +
                    "      \"owner\": {\n" +
                    "        \"id\": 6,\n" +
                    "        \"firstName\": \"Ozan\",\n" +
                    "        \"lastName\": \"Numan\",\n" +
                    "        \"email\": \"yilmazz2022@gemail.com\",\n" +
                    "        \"userType\": \"REGULAR_USER\",\n" +
                    "        \"registrationDate\": \"2025-04-28\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 19,\n" +
                    "      \"likers\": [],\n" +
                    "      \"tags\": [\n" +
                    "        {\n" +
                    "          \"id\": 30,\n" +
                    "          \"name\": \"Skyline\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"id\": 29,\n" +
                    "          \"name\": \"Atakule\"\n" +
                    "        }\n" +
                    "      ],\n" +
                    "      \"images\": [],\n" +
                    "      \"body\": \"Soaring high at Atakule, Ankara's iconic tower. Enjoying breathtaking city views and a unique dining experience! \uD83D\uDE0D\",\n" +
                    "      \"created\": \"2025-04-28\",\n" +
                    "      \"owner\": {\n" +
                    "        \"id\": 7,\n" +
                    "        \"firstName\": \"Larry\",\n" +
                    "        \"lastName\": \"Brick\",\n" +
                    "        \"email\": \"yilmazz2022@gemail.com\",\n" +
                    "        \"userType\": \"REGULAR_USER\",\n" +
                    "        \"registrationDate\": \"2025-04-28\"\n" +
                    "      }\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"id\": 20,\n" +
                    "      \"likers\": [],\n" +
                    "      \"tags\": [\n" +
                    "        {\n" +
                    "          \"id\": 32,\n" +
                    "          \"name\": \"AncientSite\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "          \"id\": 31,\n" +
                    "          \"name\": \"RomaHamamı\"\n" +
                    "        }\n" +
                    "      ],\n" +
                    "      \"images\": [],\n" +
                    "      \"body\": \"Uncovering the ancient Roma Hamamı, a hidden gem in Ankara's city center. Stepping into a bygone era and imagining the Roman baths! \uD83D\uDEC1\",\n" +
                    "      \"created\": \"2025-04-28\",\n" +
                    "      \"owner\": {\n" +
                    "        \"id\": 7,\n" +
                    "        \"firstName\": \"Larry\",\n" +
                    "        \"lastName\": \"Brick\",\n" +
                    "        \"email\": \"yilmazz2022@gemail.com\",\n" +
                    "        \"userType\": \"REGULAR_USER\",\n" +
                    "        \"registrationDate\": \"2025-04-28\"\n" +
                    "      }\n" +
                    "    }\n" +
                    "  ]\n"+
                    "}\n", new TypeReference<List<PostDTO>>() {
            });
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }
    @Override
    public List<PostDTO> fetchPosts(int page, int size, String sort) {
        return postDTOList;
    }

    @Override
    public Call<PostDTO> createPost(PostDTO post) {
        return null;
    }
    public static void main(String[] args){
        System.out.println(new PostServiceMock().fetchPosts(1,10,""));
    }
}
