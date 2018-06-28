package com.example.oleg.newspaper.Model;

import java.util.List;

public class Source {

    private String status;
    private List<ListSource> sources;

    public static class ListSource {
        private String id;
        private String name;
        private String description;
        private String url;
        private String category;
        private String language;
        private String country;
        private UrlsToLogos urlsToLogos;
        private List<String> sortBysAvailable;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String getUrl() {
            return url;
        }

        public String getCategory() {
            return category;
        }

        public String getLanguage() {
            return language;
        }

        public String getCountry() {
            return country;
        }

        public UrlsToLogos getUrlsToLogos() {
            return urlsToLogos;
        }

        public List<String> getSortBysAvailable() {
            return sortBysAvailable;
        }

        public static class UrlsToLogos {
            private String small;
            private String medium;
            private String large;

            public String getSmall() {
                return small;
            }

            public String getMedium() {
                return medium;
            }

            public String getLarge() {
                return large;
            }
        }
    }

    public String getStatus() {
        return status;
    }

    public List<ListSource> getSources() {
        return sources;
    }
}
