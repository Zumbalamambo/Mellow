package com.kevin.mellow.presenter;

import com.google.gson.Gson;
import com.kevin.mellow.base.BaseObserver;
import com.kevin.mellow.bean.WeatherBean;
import com.kevin.mellow.bean.WeatherCityBean;
import com.kevin.mellow.contract.WeatherContract;
import com.kevin.mellow.data.source.RequestDataSource;
import com.kevin.mellow.utils.LogK;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by <a href="http://blog.csdn.net/student9128">Kevin</a> on 2018/6/8.
 * <h3>
 * Describe:
 * <h3/>
 */
public class WeatherPresenter implements WeatherContract.Presenter {
    private WeatherContract.View view;
    private RequestDataSource requestDataSource;
    public String TAG = getClass().getSimpleName();

    public WeatherPresenter(WeatherContract.View view, RequestDataSource requestDataSource) {
        this.view = view;
        this.requestDataSource = requestDataSource;
        this.view.setPresenter(this);
    }


    @Override
    public void requestData(String cityName) {
//        Observable<Map<String, Object>> mapObservable = requestDataSource.requestWeatherCity
//                (cityName);
//        mapObservable.subscribeOn(Schedulers.io())
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new BaseObserver<Map<String, Object>>(view, view.getUniqueTag()) {
//                    @Override
//                    public void onNext(Map<String, Object> stringObjectMap) {
//                        super.onNext(stringObjectMap);
//                        Gson gson = new Gson();
//                        String s = gson.toJson(stringObjectMap);
//                        LogK.d(TAG,"onNext:=="+s);
//                    }
//                });

        Observable<Map<String, Object>> mapObservable1 = requestDataSource.requestCityWeather("上海");
        mapObservable1.subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<Map<String, Object>>(view, view.getUniqueTag()) {
                    @Override
                    public void onNext(Map<String, Object> stringObjectMap) {
                        super.onNext(stringObjectMap);
                        Gson gson = new Gson();
                        String s = gson.toJson(stringObjectMap);
                        LogK.d(TAG, "onNext:==" + s);
                        WeatherBean weatherBean = new Gson().fromJson(s, WeatherBean.class);
                        WeatherBean.HeWeather6Bean heWeather6Bean = weatherBean.getHeWeather6()
                                .get(0);
                        view.showData(heWeather6Bean);
                    }
                });

    }
}
