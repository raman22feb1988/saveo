package com.example.saveo;

public class Viewdata extends Urltask {
    public AsyncResponse delegate = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    int id;
    int page;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String dataModels) {
        super.onPostExecute(dataModels);

        delegate.processFinish(dataModels, id, page);
    }
}