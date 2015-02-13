package com.smarthandongi.database;

/**
 * Created by user on 2015-02-04.
 */
public class ReviewDatabase {

        public ReviewDatabase(int posting_id,int review_id, String kakao_id, String kakao_nick, String reply_date ,String content) {
            super();

            this.review_id = review_id;
            this.kakao_id = kakao_id;
            this.kakao_nick = kakao_nick;
            this.reply_date = reply_date;
            this.content = content;
            this.posting_id=posting_id;

        }

        private int posting_id;
        private int review_id;
        private String kakao_id;
        private String kakao_nick;
        private String reply_date;
        private String content;
        private boolean last;
        private String notify;

        public void setPosting_id(int posting_id) {this.posting_id=posting_id;}
        public int getPosting_id() {return posting_id;}

        public void setContent(String content) {
            this.content = content;
        }
        public String getContent() {
            return content;
        }

        public void setKakao_id(String kakao_id) {
            this.kakao_id = kakao_id;
        }
        public String getKakao_id() {
            return kakao_id;
        }

        public void setKakao_nick(String kakao_nick) {
            this.kakao_nick = kakao_nick;
        }
        public String getKakao_nick() {
            return kakao_nick;
        }

        public void setLast(boolean last) {
            this.last = last;
        }
        public boolean isLast() {
            return last;
        }

        public void setReply_date(String reply_date) {
            this.reply_date = reply_date;
        }
        public String getReply_date() {
            return reply_date;
        }

        public void setReview_id(int review_id) {
            this.review_id = review_id;
        }
        public int getReview_id() {
            return review_id;
        }

    public void setNotify(String notify) {  this.notify = notify;  }
    public String getNotify() {  return notify; }

}
