package com.globalpaysolutions.yocomprorecarga.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.globalpaysolutions.yocomprorecarga.R;
import com.globalpaysolutions.yocomprorecarga.models.api.Challenge;
import com.globalpaysolutions.yocomprorecarga.presenters.ChallengesPresenterImpl;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Josué Chávez on 13/2/2018.
 */

public class ChallengesAdapter extends RecyclerView.Adapter<ChallengesAdapter.ChallengesViewHolder>
{
    private static final String TAG = ChallengesAdapter.class.getSimpleName();

    private Context mContext;
    private List<Challenge> mChallengeList;
    private ChallengesPresenterImpl mPresnter;

    @Override
    public ChallengesViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_challenges_history_item, parent, false);

        return new ChallengesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChallengesViewHolder holder, int position)
    {
        try
        {
            final Challenge challenge = mChallengeList.get(position);
            String bet;

            holder.lblBet.setText(String.valueOf(challenge.getBet()));
            holder.lblNickname.setText(challenge.getOpponentNickname());

            // Creator: if equals 1, current user has created the challenged
            if(challenge.getCreator() > 0)
            {
                holder.lblTermini.setText(R.string.label_challenge_termini_to);
                Picasso.with(mContext).load(R.drawable.ic_challenge_outgoing).into(holder.btnBetType);
            }
            else
            {
                holder.lblTermini.setText(R.string.label_challenge_termini_from);
                Picasso.with(mContext).load(R.drawable.ic_challenge_incoming).into(holder.btnBetType);
            }

            // Status: 0 = Pending, 1 = Finished
            if(challenge.getStatus() > 0)
            {
                //Result: 0 = Lose, 1 = Win, 2 = Tie
                switch (challenge.getResult())
                {
                    case 0:
                        Picasso.with(mContext).load(R.drawable.ic_challenge_lose).into(holder.imgResult);
                        bet = "-" + String.valueOf(challenge.getBet());
                        holder.lblBet.setText(bet);
                        break;
                    case 1:
                        Picasso.with(mContext).load(R.drawable.ic_challenge_win).into(holder.imgResult);
                        bet = "+" + String.valueOf(challenge.getBet());
                        holder.lblBet.setText(bet);
                        break;
                    case 2:
                        Picasso.with(mContext).load(R.drawable.ic_challenge_tie).into(holder.imgResult);
                        break;
                }
            }
            else
            {
                Picasso.with(mContext).load(R.drawable.ic_challenge_pending).into(holder.imgResult);
            }

        }
        catch (Exception ex)
        {
            Log.e(TAG, "Error on binding viewholder: " + ex.getMessage());
        }
    }

    @Override
    public int getItemCount()
    {
        return mChallengeList.size();
    }

    public ChallengesAdapter(Context context, List<Challenge> challengeList, ChallengesPresenterImpl presenter)
    {
        this.mContext = context;
        this.mChallengeList = challengeList;
        this.mPresnter = presenter;
    }

    class ChallengesViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imgResult;
        TextView lblTermini;
        ImageView btnBetType;
        TextView lblNickname;
        TextView lblBet;

        ChallengesViewHolder(View row)
        {
            super(row);
            imgResult = (ImageView) row.findViewById(R.id.imgResult);
            lblTermini = (TextView) row.findViewById(R.id.lblTermini);
            btnBetType = (ImageView) row.findViewById(R.id.btnBetType);
            lblNickname = (TextView) row.findViewById(R.id.lblNickname);
            lblBet = (TextView) row.findViewById(R.id.lblBet);
        }
    }
}
