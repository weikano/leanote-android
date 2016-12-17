package com.wkswind.leanote;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wkswind.leanote.base.BaseFragment;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Administrator on 2016-12-12.
 */

public class NoteListFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notelist, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.content);
//        FakeAdapter adapter = new FakeAdapter(getActivity());
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(10,OrientationHelper.VERTICAL));
//        recyclerView.setAdapter(adapter);
    }

//    static class FakeViewHolder extends RecyclerView.ViewHolder {
//        private TextView textView;
//        public FakeViewHolder(View itemView) {
//            super(itemView);
//            textView = (TextView) itemView;
//        }
//    }
//
//    static class FakeAdapter extends RecyclerView.Adapter<FakeViewHolder>{
//        private final Context context;
//        private ArrayList<String> fakeDatas;
//        public FakeAdapter(Context context){
//            this.context = context;
//            int count = new Random().nextInt(50)+50;
//            fakeDatas = new ArrayList<>(count);
//            for (int i = 0; i < count; i++) {
//                String item = UUID.randomUUID().toString();
//                fakeDatas.add(item.substring(0, new Random().nextInt(10)));
//            }
//        }
//        @Override
//        public FakeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            return new FakeViewHolder(LayoutInflater.from(context).inflate(R.layout.item_fake_notebook, parent ,false));
//        }
//
//        @Override
//        public void onBindViewHolder(FakeViewHolder holder, int position) {
//            String item = fakeDatas.get(position);
//            holder.textView.setText(item);
//        }
//
//        @Override
//        public int getItemCount() {
//            return fakeDatas.size();
//        }
//    }
}
