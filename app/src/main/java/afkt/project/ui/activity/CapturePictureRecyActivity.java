package afkt.project.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.List;

import afkt.project.R;
import afkt.project.base.app.BaseToolbarActivity;
import afkt.project.base.config.PathConfig;
import afkt.project.model.bean.AdapterBean;
import afkt.project.ui.widget.BaseTextView;
import butterknife.BindView;
import dev.utils.app.CapturePictureUtils;
import dev.utils.app.ResourceUtils;
import dev.utils.app.ViewUtils;
import dev.utils.app.helper.ViewHelper;
import dev.utils.app.image.ImageUtils;

/**
 * detail: CapturePictureUtils RecyclerView 截图
 * @author Ttt
 */
public class CapturePictureRecyActivity extends BaseToolbarActivity {

    // = View =
    @BindView(R.id.vid_acp_recy)
    RecyclerView vid_acp_recy;

    @Override
    public int getLayoutId() {
        return R.layout.activity_capture_picture_recy;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 截图按钮
        BaseTextView baseTextView = new BaseTextView(this);
        ViewHelper.get().setText(baseTextView, "截图").setBold(baseTextView)
                .setTextColor(baseTextView, ResourceUtils.getColor(R.color.white))
                .setTextSizeBySp(baseTextView, 15.0f)
                .setPaddingLeft(baseTextView, 30)
                .setPaddingRight(baseTextView, 30)
                .setOnClicks(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String filePath = PathConfig.AEP_DOWN_IMAGE_PATH;
                        String fileName = "recy.jpg";
                        Bitmap bitmap;

                        // 支持三种布局 GridLayoutManager、LinearLayoutManager、StaggeredGridLayoutManager
                        // 以及对于的横、竖屏效果截图
                        bitmap = CapturePictureUtils.snapshotByRecyclerView(vid_acp_recy);
                        boolean result = ImageUtils.saveBitmapToSDCardJPEG(bitmap, filePath + fileName);
                        showToast(result, "保存成功\n" + (filePath + fileName), "保存失败");
                    }
                }, baseTextView);
        vid_bt_toolbar.addView(baseTextView);
    }

    @Override
    public void initValues() {
        super.initValues();

//        vid_acp_recy.setLayoutManager(new LinearLayoutManager(this));
//        vid_acp_recy.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false));
//
//        vid_acp_recy.setLayoutManager(new GridLayoutManager(this, 3));
//        vid_acp_recy.setLayoutManager(new GridLayoutManager(this, 3, StaggeredGridLayoutManager.HORIZONTAL, false));

        vid_acp_recy.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
//        vid_acp_recy.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL));

        final List<AdapterBean> lists = AdapterBean.newAdapterBeanList(15);
        vid_acp_recy.setAdapter(new RecyclerView.Adapter() {

            @Override
            public int getItemViewType(int position) {
                return position;
            }

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new ViewHolder(ViewUtils.inflate(R.layout.adapter_capture_picture));
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                AdapterBean adapterBean = lists.get(i);
                ViewHolder holder = (ViewHolder) viewHolder;
                View view = holder.itemView;
                ViewHelper.get().setText(ViewUtils.findViewById(view, R.id.vid_acp_title_tv), adapterBean.title)
                        .setText(ViewUtils.findViewById(view, R.id.vid_acp_content_tv), adapterBean.content);
            }

            @Override
            public int getItemCount() {
                return lists.size();
            }

            class ViewHolder extends RecyclerView.ViewHolder {

                public ViewHolder(@NonNull View itemView) {
                    super(itemView);
                }
            }
        });
    }
}